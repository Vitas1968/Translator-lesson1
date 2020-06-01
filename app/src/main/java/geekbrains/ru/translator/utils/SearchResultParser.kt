package geekbrains.ru.translator.utils

import com.google.vitaly.model.data.DataModel
import com.google.vitaly.model.data.dto.SearchResultDto
import com.google.vitaly.model.data.userdata.Meaning
import com.google.vitaly.model.data.userdata.Result
import com.google.vitaly.model.data.userdata.TranslatedMeaning

fun mapSearchResultToResult(searchResults: List<SearchResultDto>): List<Result> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            //Check for null for HistoryScreen
            meanings = it.map { meaningsDto ->
                Meaning(
                    TranslatedMeaning(meaningsDto?.translation?.translation ?: ""),
                    meaningsDto?.imageUrl ?: ""
                )
            }
        }
        Result(searchResult.text ?: "", meanings)
    }
}
fun parseOnlineSearchResults(data: DataModel): DataModel {
    return DataModel.Success(mapResult(data, true))
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
        newMeanings.addAll(searchResult.meanings.filter { it.translatedMeaning.translatedMeaning.isNotBlank() })
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(Result(searchResult.text, newMeanings))
        }
    }
}

fun convertMeaningsToSingleString(meanings: List<Meaning>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translatedMeaning.translatedMeaning, ", ")
        } else {
            meaning.translatedMeaning.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}
