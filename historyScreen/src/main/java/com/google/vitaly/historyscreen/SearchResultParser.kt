package com.google.vitaly.historyscreen

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.Meanings
import com.google.vitaly.model.data.SearchResult


fun parseLocalSearchResults(data: DataModel): DataModel {
    return DataModel.Success(mapResult(data, false))
}

private fun mapResult(
    data: DataModel,
    isOnline: Boolean
): List<SearchResult> {
    val newSearchResults = arrayListOf<SearchResult>()
    when (data) {
        is DataModel.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: DataModel.Success,
    isOnline: Boolean,
    newSearchResults: ArrayList<SearchResult>
) {
    val searchResults: List<SearchResult> = data.data as List<SearchResult>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) {
                parseOnlineResult(searchResult, newSearchResults)
            }
        } else {
            for (searchResult in searchResults) {
                newSearchResults.add(SearchResult(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(searchResult: SearchResult, newSearchResults: ArrayList<SearchResult>) {
    if (!searchResult.text.isNullOrBlank() && !searchResult.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in searchResult.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl,meaning.previewUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(SearchResult(searchResult.text, newMeanings))
        }
    }
}
