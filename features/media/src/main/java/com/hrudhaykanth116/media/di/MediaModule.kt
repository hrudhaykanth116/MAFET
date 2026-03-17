package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.BuildConfig
import com.hrudhaykanth116.media.data.network.PexelsApisService
import com.hrudhaykanth116.media.data.network.PexelsRemoteDataSource
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import com.hrudhaykanth116.media.ui.screens.MediaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mediaModule = module {
    // API Key
    single(named("pexels_api_key")) { BuildConfig.PEXELS_API_KEY }

    // Base URL
    single(named("media_baseurl")) { "https://api.pexels.com/" }

    // Retrofit instance for Media
    single<Retrofit>(named("media_retrofit")) {
        get<Retrofit.Builder>()
            .baseUrl(get<String>(named("media_baseurl")))
            .build()
    }

    // API Service
    single<PexelsApisService> {
        get<Retrofit>(named("media_retrofit")).create(PexelsApisService::class.java)
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
        PexelsRepository(get())
    }

    // ViewModel
    viewModel {
        MediaViewModel(
            get(),
            get()
        )
    }
}
