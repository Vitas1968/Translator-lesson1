package com.anikin.aleksandr.simplevocabulary.viewmodel

import geekbrains.ru.translator.model.data.DataModel

interface Interactor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel
}