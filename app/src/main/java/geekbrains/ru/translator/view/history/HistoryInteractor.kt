package geekbrains.ru.translator.view.history

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.data.SearchResult
import geekbrains.ru.translator.model.repository.Repository
import geekbrains.ru.translator.model.repository.RepositoryLocal


class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResult>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}
