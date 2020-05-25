package com.google.vitaly.repository

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.room.HistoryDao


class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> {
        val listHistoryEntity=historyDao.all()
        return mapHistoryEntityToSearchResult(
            listHistoryEntity
        )
    }

    override suspend fun saveToDB(dataModel: DataModel) {
        convertDataModelSuccessToEntity(dataModel)
            ?.let {
                historyDao.insert(it)
        }
    }
}
