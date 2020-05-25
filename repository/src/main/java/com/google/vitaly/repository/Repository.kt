package com.google.vitaly.repository



interface Repository<T> {
    suspend fun getData(word: String): T
}
