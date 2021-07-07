package com.google.vitaly.historyscreen.view.history

import com.anikin.aleksandr.simplevocabulary.viewmodel.Interactor
import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.Repository
import com.google.vitaly.repository.RepositoryLocal


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
