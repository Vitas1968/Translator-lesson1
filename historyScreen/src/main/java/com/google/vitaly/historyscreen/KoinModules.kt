package com.google.vitaly.historyscreen

import com.google.vitaly.historyscreen.view.history.HistoryActivity
import com.google.vitaly.historyscreen.view.history.HistoryInteractor
import com.google.vitaly.historyscreen.view.history.HistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(historyScreen))
}

val historyScreen =  module {
    scope(named<HistoryActivity>()) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { HistoryViewModel(get()) }
    }
}
