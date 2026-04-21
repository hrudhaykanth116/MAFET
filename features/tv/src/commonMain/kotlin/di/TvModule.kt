package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.core.common.di.DispatchersEnum
import com.hrudhaykanth116.core.ui.download.ImageDownloadManager
import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.datasources.local.room.TvDb
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import com.hrudhaykanth116.tv.data.datasources.local.room.getTvDatabaseBuilder
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domain.repository.IMyTvListRepository
import com.hrudhaykanth116.tv.domain.repository.ITvRepository
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import com.hrudhaykanth116.tv.domain.usecases.AddMyTvUseCase
import com.hrudhaykanth116.tv.domain.usecases.DeleteMyTvUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetAllTvShowsUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetMyTvListUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetTvDetailsUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetTvListByQuery
import com.hrudhaykanth116.tv.domain.usecases.IsTvBookmarkedUseCase
import com.hrudhaykanth116.tv.domain.usecases.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.ui.screens.all.TvHomeViewModel
import com.hrudhaykanth116.tv.ui.screens.details.TvDetailsViewModel
import com.hrudhaykanth116.tv.ui.screens.home.EntertainmentHomeScreenViewModel
import com.hrudhaykanth116.tv.ui.screens.home.UpdateMyTvViewModel
import com.hrudhaykanth116.tv.ui.screens.search.SearchTvScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tvCommonModule = module {

    // Network - Ktor API Service (uses HttpClient from core-network)
    single<TmdbApiServiceKtor> {
        TmdbApiServiceKtor(
            httpClient = get<HttpClient>()
        )
    }

    // Database
    single<TvDb> {
        getTvDatabaseBuilder().build()
    }

    single<MyTvListDao> {
        get<TvDb>().myTvListDao()
    }

    // Data Sources
    single<MyTvListLocalDataSource> {
        MyTvListLocalDataSource(get())
    }

    single<TvRemoteDataSource> {
        TvRemoteDataSource(get())
    }

    single<TvShowsRemoteDataSource> {
        TvShowsRemoteDataSource(get())
    }

    // Repositories (bound to interfaces)
    single<IMyTvListRepository> {
        MyTvListRepository(get())
    }

    single<ITvRepository> {
        TvRepository(get(), get(named(DispatchersEnum.IoDispatcher)))
    }

    single<ITvShowsRepository> {
        TvShowsRepository(get(), get(named(DispatchersEnum.IoDispatcher)))
    }

    // Use Cases
    single<GetAllTvShowsUseCase> {
        GetAllTvShowsUseCase(get())
    }

    single<IsTvBookmarkedUseCase> {
        IsTvBookmarkedUseCase(get())
    }

    single<GetTvDetailsUseCase> {
        GetTvDetailsUseCase(get(), get(), get())
    }

    single<AddMyTvUseCase> {
        AddMyTvUseCase(get(), get())
    }

    single<GetMyTvListUseCase> {
        GetMyTvListUseCase(get())
    }

    single<DeleteMyTvUseCase> {
        DeleteMyTvUseCase(get())
    }

    single<UpdateMyTvUseCase> {
        UpdateMyTvUseCase(get())
    }

    single<GetTvListByQuery> {
        GetTvListByQuery(get(), get())
    }

    // ViewModels (non-paging)
    factory {
        TvHomeViewModel(get(), get())
    }

    factory {
        TvDetailsViewModel(get(), get(), get(), get(), get(), get(), get<ImageDownloadManager>())
    }

    factory {
        EntertainmentHomeScreenViewModel(get(), get(), get(), get())
    }

    factory {
        UpdateMyTvViewModel(get(), get())
    }

    factory {
        SearchTvScreenViewModel(get(), get(), get())
    }
}

expect val tvPlatformModule: Module

val tvModule: List<Module> = listOf(tvCommonModule, tvPlatformModule)
