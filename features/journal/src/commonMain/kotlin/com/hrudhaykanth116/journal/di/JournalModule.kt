package com.hrudhaykanth116.journal.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.journal.data.data_source.local.IJournalLocalDataSource
import com.hrudhaykanth116.journal.data.data_source.local.JournalLocalDataSource
import com.hrudhaykanth116.journal.data.local.room.dao.JournalEntriesDao
import com.hrudhaykanth116.journal.data.local.room.dbs.JournalDb
import com.hrudhaykanth116.journal.data.local.room.dbs.getDatabaseBuilder
import com.hrudhaykanth116.journal.data.repositories.JournalRepository
import com.hrudhaykanth116.journal.domain.repository.IJournalRepository
import com.hrudhaykanth116.journal.domain.use_cases.CreateEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.DeleteEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.GetEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.ObserveEntriesUseCase
import com.hrudhaykanth116.journal.domain.use_cases.UpdateEntryUseCase
import com.hrudhaykanth116.journal.ui.mappers.JournalUIMapper
import com.hrudhaykanth116.journal.ui.screens.entry.JournalEntryViewModel
import com.hrudhaykanth116.journal.ui.screens.list.JournalListViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val journalModule = module {
    single<JournalDb> {
        getDatabaseBuilder().build()
    }

    single<JournalEntriesDao> { get<JournalDb>().journalEntriesDao() }

    single<IJournalLocalDataSource> { JournalLocalDataSource(get()) }

    single<IJournalRepository> {
        JournalRepository(
            localDataSource = get(),
            timeProvider = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }

    factory { ObserveEntriesUseCase(get()) }
    factory { GetEntryUseCase(get()) }
    factory { CreateEntryUseCase(get()) }
    factory { UpdateEntryUseCase(get()) }
    factory { DeleteEntryUseCase(get()) }

    factory { JournalUIMapper(get()) }

    factory {
        JournalListViewModel(
            observeEntriesUseCase = get(),
            deleteEntryUseCase = get(),
            networkMonitor = get(),
            mapper = get(),
            dispatcher = get(named(DispatchersEnum.MainDispatcher))
        )
    }

    factory { (entryId: String?) ->
        JournalEntryViewModel(
            createEntryUseCase = get(),
            updateEntryUseCase = get(),
            getEntryUseCase = get(),
            networkMonitor = get(),
            uniqueIdGenerator = get(),
            dateTimeUtils = get(),
            timeProvider = get(),
            dispatcher = get(named(DispatchersEnum.MainDispatcher)),
            entryId = entryId
        )
    }
}
