package geekbrains.ru.translator.di

import androidx.room.Room
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.RetrofitImplementation
import com.google.vitaly.repository.RoomDataBaseImplementation
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryImplementation
import com.google.vitaly.repository.RepositoryImplementationLocal
import com.google.vitaly.repository.RepositoryLocal
import com.google.vitaly.historyscreen.view.history.HistoryInteractor
import com.google.vitaly.historyscreen.view.history.HistoryViewModel
import com.google.vitaly.repository.room.HistoryDataBase
import geekbrains.ru.translator.view.main.MainInteractor
import geekbrains.ru.translator.view.main.MainViewModel
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResult>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<RepositoryLocal<List<SearchResult>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
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
