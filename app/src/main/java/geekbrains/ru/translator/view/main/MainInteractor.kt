package geekbrains.ru.translator.view.main

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryLocal


class MainInteractor(
    private val remoteRepository: Repository<List<SearchResult>>,
    private val localRepository: RepositoryLocal<List<SearchResult>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}
