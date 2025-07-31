package com.hrudhaykanth116.tv.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.domaintemp.DeleteMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetMyTvListUseCase
import com.hrudhaykanth116.tv.domaintemp.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState
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
    private val dateTimeUtils: DateTimeUtils,
    private val deleteMyTvUseCase: DeleteMyTvUseCase,

    ) :
    UDFViewModel<TvHomeScreenUIState, TvHomeScreenEvent, TvHomeScreenEffect>(TvHomeScreenUIState()) {

    init {
        viewModelScope.launch {
            getMyTvListUseCase().collectLatest {
                Log.d(TAG, "tv list: $it")
                setState {
                    copy(
                        tvShows = it.toUIState(dateTimeUtils),
                    )
                }
            }
        }


    }

    override fun processEvent(event: TvHomeScreenEvent) {

        when (event) {
            is TvHomeScreenEvent.Delete -> deleteMyTv(event.id)
            is TvHomeScreenEvent.MyTvListItemClicked -> onMyTvListItemClicked(event.myTv)
            TvHomeScreenEvent.CloseUpdateTv -> closeUpdateTv()
        }

    }

    private fun deleteMyTv(id: Int){
        viewModelScope.launch {
            deleteMyTvUseCase(id)
        }
    }

    private fun closeUpdateTv() {
        setState {
            copy(
                updateTv = null,
            )
        }
    }

    private fun onMyTvListItemClicked(myTv: MyTvUIState) {
        setState {
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

    companion object{
        private const val TAG = "TvHomeScreenViewModel"
    }


}