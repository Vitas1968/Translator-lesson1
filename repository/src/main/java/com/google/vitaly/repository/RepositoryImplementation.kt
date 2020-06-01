package com.google.vitaly.repository

import com.google.vitaly.model.data.dto.SearchResultDto

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResultDto>>) :
    Repository<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return dataSource.getData(word)
    }
}
