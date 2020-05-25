package geekbrains.ru.translator.view.main

import androidx.lifecycle.LiveData
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.historyscreen.parseOnlineSearchResults
import geekbrains.ru.translator.viewmodel.BaseViewModel
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
        _mutableLiveData.postValue(
            parseOnlineSearchResults(
                interactor.getData(word, isOnline)
            )
        )
    }

    override fun onCleared() {
        _mutableLiveData.value = DataModel.Success(null)
        super.onCleared()
    }
}
