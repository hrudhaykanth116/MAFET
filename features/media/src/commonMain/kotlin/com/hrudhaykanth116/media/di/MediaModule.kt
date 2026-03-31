package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.media.data.network.PexelsRemoteDataSource
import com.hrudhaykanth116.media.data.network.ktor.PexelsApiServiceKtor
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import com.hrudhaykanth116.media.domain.usecases.GetCuratedMediaUseCase
import com.hrudhaykanth116.media.domain.usecases.GetMediaDetailUseCase
import com.hrudhaykanth116.media.domain.usecases.SearchMediaUseCase
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.platform.MediaPlatformActions
import com.hrudhaykanth116.media.ui.screens.MediaViewModel
import com.hrudhaykanth116.media.ui.screens.detail.MediaDetailViewModel
import com.hrudhaykanth116.media.ui.screens.home.MediaHomeViewModel
import com.hrudhaykanth116.media.ui.screens.search.MediaSearchViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val PEXELS_API_KEY = "PEXELS_KEY_REMOVED"

expect val platformMediaModule: org.koin.core.module.Module

val mediaModule = module {
    includes(platformMediaModule)
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

    // Use Cases
    single<GetCuratedMediaUseCase> {
        GetCuratedMediaUseCase(get())
    }

    single<SearchMediaUseCase> {
        SearchMediaUseCase(get())
    }

    single<GetMediaDetailUseCase> {
        GetMediaDetailUseCase(get())
    }

    // ViewModels
    viewModel {
        MediaViewModel(
            get(),
            get()
        )
    }

    viewModel {
        MediaHomeViewModel(
            getCuratedMediaUseCase = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }

    viewModel { (mediaId: Int, mediaType: MediaType) ->
        MediaDetailViewModel(
            getMediaDetailUseCase = get(),
            platformActions = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher)),
            mediaId = mediaId,
            mediaType = mediaType
        )
    }

    viewModel {
        MediaSearchViewModel(
            searchMediaUseCase = get(),
            networkMonitor = get(),
            dispatcher = get(named(DispatchersEnum.IoDispatcher))
        )
    }
}
