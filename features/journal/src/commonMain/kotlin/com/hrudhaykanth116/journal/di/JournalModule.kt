package com.hrudhaykanth116.journal.di

import com.hrudhaykanth116.journal.ui.screens.JournalViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val journalModule = module {
    viewModel {
        JournalViewModel(
            networkMonitor = get(),
            dispatcher = get()
        )
    }
}
