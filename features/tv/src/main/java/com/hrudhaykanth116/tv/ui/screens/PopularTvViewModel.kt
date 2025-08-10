package com.hrudhaykanth116.tv.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowData
import com.hrudhaykanth116.tv.data.repositories.tv.PopularTvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularTvViewModel @Inject constructor(
    private val popularTvShowsRepository: PopularTvShowsRepository
): ViewModel(){

    init {



    }

    fun getPopularTvShows(): Flow<PagingData<TvShowData>> {
        return popularTvShowsRepository.getTvShows().cachedIn(viewModelScope)

    }

    // fun getPopularTvShows(): Flow<PagingData<TvShowData>> {
    //     if(popularTvShowsLiveData != null){
    //         return popularTvShowsLiveData!!
    //     }
    //     val newResult: Flow<PagingData<TvShowData>> =
    //         popularTvShowsRepository.getTvShows().cachedIn(viewModelScope)
    //     popularTvShowsLiveData = newResult
    //     return newResult
    // }

    companion object {
        private const val TAG = "PopularTvViewModel"
    }

}