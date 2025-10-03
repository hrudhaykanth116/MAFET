package com.hrudhaykanth116.tv.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.domaintemp.DeleteMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetMyTvListUseCase
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenEffect
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenEvent
import com.hrudhaykanth116.tv.ui.models.home.EntertainmentHomeScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntertainmentHomeScreenViewModel @Inject constructor(
    private val getMyTvListUseCase: GetMyTvListUseCase,
    private val dateTimeUtils: DateTimeUtils,
    private val deleteMyTvUseCase: DeleteMyTvUseCase,
    private val networkMonitor: NetworkMonitor,
) : UIStateViewModel<EntertainmentHomeScreenUIState, EntertainmentHomeScreenEvent, EntertainmentHomeScreenEffect>(
    initialState = UIState.Idle(),
    defaultState = EntertainmentHomeScreenUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        viewModelScope.launch {
            getMyTvListUseCase().collectLatest {
                Log.d(TAG, "tv list: $it")
                setIdleState {
                    copy(
                        tvShows = it.toUIState(dateTimeUtils),
                    )
                }
            }
        }
    }

    override fun processEvent(event: EntertainmentHomeScreenEvent) {

        when (event) {
            is EntertainmentHomeScreenEvent.Delete -> deleteMyTv(event.id)
            is EntertainmentHomeScreenEvent.MyEntertainmentListItemClicked -> onMyTvListItemClicked(
                event.myTv
            )

            EntertainmentHomeScreenEvent.CloseUpdateTv -> closeUpdateTv()
        }

    }

    private fun deleteMyTv(id: Int) {
        viewModelScope.launch {
            deleteMyTvUseCase(id)
        }
    }

    private fun closeUpdateTv() {
        setIdleState {
            copy(
                updateTv = null
            )
        }
    }

    private fun onMyTvListItemClicked(myTv: MyTvUIState) {
        setIdleState {
            copy(
                updateTv = myTv
            )
        }
    }

    private fun onAddNewEvent() {
        // setState {
        //     copy(
        //         shouldNavigateToSearchScreen = true
        //     )
        // }
    }

    companion object Companion {
        private const val TAG = "TvHomeScreenViewModel"
    }


}