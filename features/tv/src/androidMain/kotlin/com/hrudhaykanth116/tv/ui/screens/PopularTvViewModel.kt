package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.repositories.tv.AiringTodayShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TopRatedTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TrendingTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domain.models.TvCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularTvViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val popularTvShowsRepository: PopularTvShowsRepository,
    private val topRatedTvShowsRepository: TopRatedTvShowsRepository,
    private val trendingTvShowsRepository: TrendingTvShowsRepository,
    private val airingTodayShowsRepository: AiringTodayShowsRepository,
    private val tvShowsRepository: TvShowsRepository,
): ViewModel(){

    private val categoryParam: String = savedStateHandle.get<String>("category") ?: TvCategory.POPULAR.routeParam
    val category: TvCategory = TvCategory.fromRouteParam(categoryParam) ?: TvCategory.POPULAR

    val popularTvShows: Flow<PagingData<TvShowData>> = when (category) {
        TvCategory.POPULAR -> popularTvShowsRepository.getTvShows()
        TvCategory.TOP_RATED -> topRatedTvShowsRepository.getTvShowsPagingData()
        TvCategory.TRENDING -> trendingTvShowsRepository.getTvShowsPagingData()
        TvCategory.AIRING_TODAY -> airingTodayShowsRepository.getTvShowsPagingData()
    }.cachedIn(viewModelScope)

    fun initialiseData(){

        viewModelScope.launch {
            val tvGenresDataResultDeferred = async {
                tvShowsRepository.getTvGenres()
            }
        }

    }

    companion object {
        private const val TAG = "PopularTvViewModel"
    }

}