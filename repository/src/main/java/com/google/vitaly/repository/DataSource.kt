package com.google.vitaly.repository


interface DataSource<T> {
    suspend fun getData(word: String): T
}
