package com.google.vitaly.model.data

import com.google.gson.annotations.SerializedName
import com.google.vitaly.model.data.Meanings

class SearchResult(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)
