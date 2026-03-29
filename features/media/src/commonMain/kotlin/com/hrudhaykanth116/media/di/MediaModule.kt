package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.media.data.network.PexelsRemoteDataSource
import com.hrudhaykanth116.media.data.network.ktor.PexelsApiServiceKtor
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import com.hrudhaykanth116.media.ui.screens.MediaViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val PEXELS_API_KEY = "JRWFPyxMObCfYwIpDE7ZVJ4iGeo0Rv3sMeucQjjw8l1WD9wSMUnZophQ"

val mediaModule = module {
    // API Key
    single(named("pexels_api_key")) { PEXELS_API_KEY }

    // Network - Ktor API Service (uses HttpClient from core-network)
    single<PexelsApiServiceKtor> {
        PexelsApiServiceKtor(
            httpClient = get<HttpClient>()
        )
    }

    // Remote Data Source
    single<PexelsRemoteDataSource> {
        PexelsRemoteDataSource(
            get(),
            get(named("pexels_api_key"))
        )
    }

    // Repository
    single<PexelsRepository> {
        PexelsRepository(
            get(),
            get(named(DispatchersEnum.IoDispatcher))
        )
    }

    // ViewModel
    viewModel {
        MediaViewModel(
            get(),
            get()
        )
    }
}
