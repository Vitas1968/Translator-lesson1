package com.google.vitaly.repository

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.SearchResult
import com.google.vitaly.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResult> {
    val searchResult = ArrayList<SearchResult>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(SearchResult(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(dataModel: DataModel): HistoryEntity? {
    return when (dataModel) {
        is DataModel.Success -> {
            val searchResult = dataModel.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(searchResult[0].text!!, null)
            }
        }
        else -> null
    }
}