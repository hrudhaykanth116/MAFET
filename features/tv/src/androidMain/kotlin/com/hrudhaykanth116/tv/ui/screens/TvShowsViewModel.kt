package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.repositories.tv.TvPagingRepository
import com.hrudhaykanth116.tv.domain.models.TvCategory
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TvShowsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val tvPagingRepository: TvPagingRepository,
    private val tvShowsRepository: ITvShowsRepository,
) : ViewModel() {

    private val categoryParam: String = savedStateHandle.get<String>("category") ?: TvCategory.POPULAR.routeParam
    val category: TvCategory = TvCategory.fromRouteParam(categoryParam) ?: TvCategory.POPULAR

    val tvShows: Flow<PagingData<TvShowData>> =
        tvPagingRepository.getTvShowsPagingData(category).cachedIn(viewModelScope)

    fun initialiseData() {
        viewModelScope.launch {
            async { tvShowsRepository.getTvGenres() }
        }
    }

    companion object {
        private const val TAG = "PopularTvViewModel"
    }
}
