package com.hrudhaykanth116.tv.ui.screens

import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.data.repositories.tv.TvShowsRepository
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEffect
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvHomeScreenViewModel @Inject constructor(
    private val tvShowsRepository: TvShowsRepository,
) : UDFViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(TvHomeScreenUIState()) {

    init {

    }

    fun fetchData(){

    }

    override fun processEvent(event: TvHomeScreenEvent) {

    }


}