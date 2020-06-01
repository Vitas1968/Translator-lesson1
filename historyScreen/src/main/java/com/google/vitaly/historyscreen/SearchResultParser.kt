package com.google.vitaly.historyscreen

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.userdata.Meaning
import com.google.vitaly.model.data.userdata.Result


fun parseLocalSearchResults(data: DataModel): DataModel {
    return DataModel.Success(mapResult(data, false))
}

private fun mapResult(
    data: DataModel,
    isOnline: Boolean
): List<Result> {
    val newSearchResults = arrayListOf<Result>()
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
    newSearchResults: ArrayList<Result>
) {
    val searchResults: List<Result> = data.data as List<Result>
    if (searchResults.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchResults) {
                parseOnlineResult(searchResult, newSearchResults)
            }
        } else {
            for (searchResult in searchResults) {
                newSearchResults.add(Result(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(searchResult: Result, newSearchResults: ArrayList<Result>) {
    if (searchResult.text.isNotBlank() && searchResult.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        for (meaning in searchResult.meanings) {
            if (meaning.translatedMeaning.translatedMeaning.isBlank()) {
                newMeanings.add(
                    Meaning(
                        meaning.translatedMeaning,
                        meaning.imageUrl
                    )
                )
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(
                Result(
                    searchResult.text,
                    newMeanings
                )
            )
        }
    }
}
