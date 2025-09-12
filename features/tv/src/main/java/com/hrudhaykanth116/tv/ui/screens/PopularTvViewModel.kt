package com.hrudhaykanth116.tv.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularTvViewModel @Inject constructor(
    private val popularTvShowsRepository: PopularTvShowsRepository,
    private val tvShowsRepository: TvShowsRepository,
): ViewModel(){

    val popularTvShows: Flow<PagingData<TvShowData>> =
        popularTvShowsRepository.getTvShows()
            .cachedIn(viewModelScope)

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