package com.google.vitaly.repository

import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.DataSource
import com.google.vitaly.repository.Repository

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>) :
    Repository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}
