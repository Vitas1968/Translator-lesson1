package geekbrains.ru.translator.di

import androidx.room.Room
import com.google.vitaly.model.data.dto.SearchResultDto
import com.google.vitaly.repository.RetrofitImplementation
import com.google.vitaly.repository.RoomDataBaseImplementation
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryImplementation
import com.google.vitaly.repository.RepositoryImplementationLocal
import com.google.vitaly.repository.RepositoryLocal
import com.google.vitaly.repository.room.HistoryDataBase
import geekbrains.ru.translator.view.main.MainActivity
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun injectDependencies() = loadModules
private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResultDto>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResultDto>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get())) }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped {
            MainInteractor(get(), get()) }
        viewModel {
            MainViewModel(get()) }
    }
}



