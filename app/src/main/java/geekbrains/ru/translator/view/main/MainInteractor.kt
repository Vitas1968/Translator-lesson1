package geekbrains.ru.translator.view.main

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryLocal


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
