package com.hrudhaykanth116.tv.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.domaintemp.GetMyTvListUseCase
import com.hrudhaykanth116.tv.domaintemp.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEffect
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.TvHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvHomeScreenViewModel @Inject constructor(
    private val getMyTvListUseCase: GetMyTvListUseCase,
    private val updateMyTvUseCase: UpdateMyTvUseCase,
) : UDFViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(TvHomeScreenUIState()) {

    init {
        viewModelScope.launch {
            getMyTvListUseCase().collectLatest {
                it.toUIState()
                setState {
                    copy(
                        tvShows = it.toUIState(),
                    )
                }
            }
        }


    }

    override fun processEvent(event: TvHomeScreenEvent) {

        when (event) {
            TvHomeScreenEvent.AddNew -> onAddNewEvent()
            is TvHomeScreenEvent.Delete -> onAddNewEvent()
            is TvHomeScreenEvent.MyTvListItemClicked -> onAddNewEvent()
        }

    }

    private fun onAddNewEvent() {
        // setState {
        //     copy(
        //         shouldNavigateToSearchScreen = true
        //     )
        // }
    }


}