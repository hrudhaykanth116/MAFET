package com.hrudhaykanth116.core.data.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.core.data.local.datastore.createDataStore
import com.hrudhaykanth116.core.data.repository.UserPreferencesRepositoryImpl
import com.hrudhaykanth116.core.domain.repository.IUserPreferencesRepository
import com.hrudhaykanth116.core.domain.usecases.ClearSavedUserLocationUseCase
import com.hrudhaykanth116.core.domain.usecases.GetSavedUserLocationUseCase
import com.hrudhaykanth116.core.domain.usecases.ObserveSavedUserLocationUseCase
import com.hrudhaykanth116.core.domain.usecases.SaveUserLocationUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreDataModule = module {
    single { createDataStore() }

    single<IUserPreferencesRepository> {
        UserPreferencesRepositoryImpl(
            dataStore = get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }

    factory { SaveUserLocationUseCase(get()) }
    factory { GetSavedUserLocationUseCase(get()) }
    factory { ObserveSavedUserLocationUseCase(get()) }
    factory { ClearSavedUserLocationUseCase(get()) }
}
