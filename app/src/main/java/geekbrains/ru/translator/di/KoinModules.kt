package geekbrains.ru.translator.di

import androidx.room.Room
import com.google.vitaly.model.data.SearchResult
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import geekbrains.ru.translator.model.repository.RepositoryImplementationLocal
import geekbrains.ru.translator.model.repository.RepositoryLocal
import geekbrains.ru.translator.room.HistoryDataBase
import com.google.vitaly.historyscreen.view.history.HistoryInteractor
import com.google.vitaly.historyscreen.view.history.HistoryViewModel
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.view.main.MainViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResult>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResult>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInteractor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory {
        HistoryInteractor(
            get(),
            get()
        )
    }
}
