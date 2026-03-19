package com.hrudhaykanth116.tv.di

import androidx.room.Room
import com.hrudhaykanth116.tv.data.datasources.local.MyTvListLocalDataSource
import com.hrudhaykanth116.tv.data.datasources.local.room.TvDb
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import com.hrudhaykanth116.tv.data.datasources.remote.ktor.TmdbApiServiceKtor
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvRemoteDataSource
import com.hrudhaykanth116.tv.data.datasources.remote.sources.tvshows.TvShowsRemoteDataSource
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domaintemp.AddMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.DeleteMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetAllTvShowsUseCase
import com.hrudhaykanth116.tv.domaintemp.GetMyTvListUseCase
import com.hrudhaykanth116.tv.domaintemp.GetTvDetailsUseCase
import com.hrudhaykanth116.tv.domaintemp.GetTvListByQuery
import com.hrudhaykanth116.tv.domaintemp.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.ui.screens.PopularTvViewModel
import com.hrudhaykanth116.tv.ui.screens.all.TvHomeViewModel
import com.hrudhaykanth116.tv.ui.screens.details.TvDetailsViewModel
import com.hrudhaykanth116.tv.ui.screens.home.EntertainmentHomeScreenViewModel
import com.hrudhaykanth116.tv.ui.screens.home.UpdateMyTvViewModel
import com.hrudhaykanth116.tv.ui.screens.search.SearchTvScreenViewModel
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tvModule = module {

    // Network - Ktor API Service (uses HttpClient from core-network)
    single<TmdbApiServiceKtor> {
        TmdbApiServiceKtor(
            httpClient = get<HttpClient>()
        )
    }

    // Database
    single<TvDb> {
        Room.databaseBuilder(
            androidContext(),
            TvDb::class.java,
            TvDb.TABLE_NAME
        ).apply {
            fallbackToDestructiveMigration()
        }.build()
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

    // Repositories
    single<MyTvListRepository> {
        MyTvListRepository(get())
    }

    single<TvRepository> {
        TvRepository(get(), get(named("IoDispatcher")))
    }

    single<TvShowsRepository> {
        TvShowsRepository(get(), get(named("IoDispatcher")))
    }

    single<PopularTvShowsRepository> {
        PopularTvShowsRepository(get())
    }

    // Use Cases
    single<GetAllTvShowsUseCase> {
        GetAllTvShowsUseCase(get())
    }

    single<GetTvDetailsUseCase> {
        GetTvDetailsUseCase(get(), get())
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

    // ViewModels
    viewModel {
        PopularTvViewModel(get(), get())
    }

    viewModel {
        TvHomeViewModel(get(), get())
    }

    viewModel {
        TvDetailsViewModel(get(), get(), get(), get())
    }

    viewModel {
        EntertainmentHomeScreenViewModel(get(), get(), get(), get())
    }

    viewModel {
        UpdateMyTvViewModel(get(), get())
    }

    viewModel {
        SearchTvScreenViewModel(get(), get(), get())
    }
}
