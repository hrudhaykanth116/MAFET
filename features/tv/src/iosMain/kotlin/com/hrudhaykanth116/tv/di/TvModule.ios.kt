package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.tv.data.repositories.tv.DiscoverTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TopRatedTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowReviewsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowSimilarShowsRepository
import com.hrudhaykanth116.tv.ui.screens.PopularTvViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val tvPlatformModule: Module = module {
    // Simple list Repositories (iOS without PagingData)
    single { PopularTvShowsRepository(get()) }
    single { TopRatedTvShowsRepository(get()) }
    single { DiscoverTvShowsRepository(get()) }
    single { TvShowReviewsRepository(get()) }
    single { TvShowSimilarShowsRepository(get()) }

    // ViewModels that use simple lists
    factory { PopularTvViewModel(get(), get()) }
}
