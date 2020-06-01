package com.google.vitaly.repository

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.dto.SearchResultDto

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultDto>>) :
    RepositoryLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        dataSource.saveToDB(dataModel)
    }
}
