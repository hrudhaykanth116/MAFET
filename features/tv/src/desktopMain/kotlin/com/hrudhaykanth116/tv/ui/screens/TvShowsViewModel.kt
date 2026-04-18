package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.tv.data.repositories.tv.TvListRepository
import com.hrudhaykanth116.tv.domain.models.TvCategory
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvShowsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val tvListRepository: TvListRepository,
    private val tvShowsRepository: ITvShowsRepository,
) : ViewModel() {

    private val categoryParam: String = savedStateHandle.get<String>("category") ?: TvCategory.POPULAR.routeParam
    val category: TvCategory = TvCategory.fromRouteParam(categoryParam) ?: TvCategory.POPULAR

    private val _uiState = MutableStateFlow(TvShowsScreenUIState())
    val uiState: StateFlow<TvShowsScreenUIState> = _uiState.asStateFlow()

    init {
        loadTvShows()
    }

    private fun loadTvShows() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            tvListRepository.getTvShows(category).collectLatest { tvShows ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    tvShows = tvShows,
                    error = null,
                )
            }
        }
    }

    fun retry() {
        loadTvShows()
    }
}
