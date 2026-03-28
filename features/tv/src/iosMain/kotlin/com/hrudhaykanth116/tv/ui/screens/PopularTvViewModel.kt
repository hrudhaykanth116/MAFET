package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularTvViewModel(
    private val popularTvShowsRepository: PopularTvShowsRepository,
    private val tvShowsRepository: TvShowsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PopularTvScreenUIState())
    val uiState: StateFlow<PopularTvScreenUIState> = _uiState.asStateFlow()

    init {
        loadTvShows()
    }

    private fun loadTvShows() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            popularTvShowsRepository.getTvShows().collectLatest { tvShows ->
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
