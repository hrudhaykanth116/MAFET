package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.tv.data.repositories.tv.AiringTodayShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TopRatedTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TrendingTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.domain.models.TvCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularTvViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val popularTvShowsRepository: PopularTvShowsRepository,
    private val topRatedTvShowsRepository: TopRatedTvShowsRepository,
    private val trendingTvShowsRepository: TrendingTvShowsRepository,
    private val airingTodayShowsRepository: AiringTodayShowsRepository,
    private val tvShowsRepository: TvShowsRepository,
) : ViewModel() {

    private val categoryParam: String = savedStateHandle.get<String>("category") ?: TvCategory.POPULAR.routeParam
    val category: TvCategory = TvCategory.fromRouteParam(categoryParam) ?: TvCategory.POPULAR

    private val _uiState = MutableStateFlow(PopularTvScreenUIState())
    val uiState: StateFlow<PopularTvScreenUIState> = _uiState.asStateFlow()

    init {
        loadTvShows()
    }

    private fun loadTvShows() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val repository = when (category) {
                TvCategory.POPULAR -> popularTvShowsRepository.getTvShows()
                TvCategory.TOP_RATED -> topRatedTvShowsRepository.getTvShowsPagingData()
                TvCategory.TRENDING -> trendingTvShowsRepository.getTvShowsPagingData()
                TvCategory.AIRING_TODAY -> airingTodayShowsRepository.getTvShowsPagingData()
            }

            repository.collectLatest { tvShows ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tvShows = tvShows,
                    error = null
                )
            }
        }
    }

    fun retry() {
        loadTvShows()
    }
}
