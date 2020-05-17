package geekbrains.ru.translator.view.main

import androidx.lifecycle.LiveData
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.utils.parseSearchResults
import geekbrains.ru.translator.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor) : BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve: LiveData<DataModel> = _mutableLiveData

    fun subscribe(): LiveData<DataModel> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
            _mutableLiveData.value = DataModel.Loading(null)
                    cancelJob()
                    viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(DataModel.Error(error))
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(parseSearchResults(interactor.getData(word, isOnline)))
    }

    override fun onCleared() {
        _mutableLiveData.value = DataModel.Success(null)
        super.onCleared()
    }
}
