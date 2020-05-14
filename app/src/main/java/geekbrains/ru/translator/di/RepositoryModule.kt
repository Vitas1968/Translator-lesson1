package geekbrains.ru.translator.di

import dagger.Module
import dagger.Provides
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.model.datasource.DataSource
import geekbrains.ru.translator.model.datasource.RetrofitImplementation
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryImplementation
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<SearchResult>>): Repository<List<SearchResult>> =
        RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<SearchResult>>): Repository<List<SearchResult>> =
        RepositoryImplementation(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<SearchResult>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<SearchResult>> = RoomDataBaseImplementation()
}
