package com.anikin.aleksandr.simplevocabulary.viewmodel

import com.google.vitaly.model.data.DataModel

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel
}