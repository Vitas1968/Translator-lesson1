package geekbrains.ru.translator.view.main

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class MainViewModel(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    )
) : BaseViewModel<DataModel>() {

    private var dataModel: DataModel? = null

    override fun getData(word: String, isOnline: Boolean): LiveData<DataModel> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = DataModel.Loading(null) }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {

            override fun onNext(data: DataModel) {
                dataModel = data
                liveDataForViewToObserve.value = data
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = DataModel.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
