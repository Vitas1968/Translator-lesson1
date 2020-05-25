package com.google.vitaly.repository.api

import com.google.vitaly.model.data.SearchResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<SearchResult>>
}
