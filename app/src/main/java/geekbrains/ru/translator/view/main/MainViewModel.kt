package geekbrains.ru.translator.view.main

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSourceLocal
import geekbrains.ru.translator.model.datasource.DataSourceRemote
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.utils.parseSearchResults
import geekbrains.ru.translator.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) : BaseViewModel<DataModel>() {

    private var dataModel: DataModel? = null

    fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }
    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = DataModel.Loading(null) }

    private fun getObserver(): DisposableObserver<DataModel> {
        return object : DisposableObserver<DataModel>() {

            override fun onNext(data: DataModel) {
                dataModel = parseSearchResults(data)
                liveDataForViewToObserve.value = dataModel
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = DataModel.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}
