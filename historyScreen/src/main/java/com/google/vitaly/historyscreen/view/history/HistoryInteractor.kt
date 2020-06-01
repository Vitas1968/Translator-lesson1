package com.google.vitaly.historyscreen.view.history

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.dto.SearchResultDto
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryLocal
import geekbrains.ru.translator.utils.mapSearchResultToResult


class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }
}
