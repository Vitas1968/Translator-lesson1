package geekbrains.ru.translator.di

import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.view.main.MainInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<SearchResult>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<SearchResult>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
