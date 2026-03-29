package com.hrudhaykanth116.ai.di

import com.hrudhaykanth116.ai.data.datasources.remote.QueryRemoteDataSource
import com.hrudhaykanth116.ai.data.repository.AIQueryRepository
import com.hrudhaykanth116.ai.domain.usecases.GetQueryResultUseCase
import com.hrudhaykanth116.ai.ui.screens.query.QueryScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val aiModule = module {

    single { QueryRemoteDataSource() }

    single { AIQueryRepository(get()) }

    factory { GetQueryResultUseCase() }

    viewModel { QueryScreenViewModel() }
}
