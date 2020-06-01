package geekbrains.ru.translator.view.main

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.dto.SearchResultDto
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryLocal
import geekbrains.ru.translator.utils.mapSearchResultToResult


class MainInteractor(
    private val remoteRepository: Repository<List<SearchResultDto>>,
    private val localRepository: RepositoryLocal<List<SearchResultDto>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        val dataModel: DataModel
        if (fromRemoteSource) {
            dataModel = DataModel.Success(mapSearchResultToResult(remoteRepository.getData(word)))
            localRepository.saveToDB(dataModel)
        } else {
            dataModel = DataModel.Success(mapSearchResultToResult(localRepository.getData(word)))
        }
        return dataModel
    }
}
