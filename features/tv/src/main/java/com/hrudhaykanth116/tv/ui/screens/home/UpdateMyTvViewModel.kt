package com.hrudhaykanth116.tv.ui.screens.home

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.domaintemp.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEffect
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMyTvViewModel @Inject constructor(
    // updateTvData: UpdateMyTvUIStateActual.UpdateTvData,
    private val updateMyTvUseCase: UpdateMyTvUseCase,
    private val dateTimeUtils: DateTimeUtils,
) : UDFViewModel<UpdateMyTvUIStateActual, UpdateMyTvScreenEvent, UpdateMyTvScreenEffect>(
    UpdateMyTvUIStateActual()
) {

    init {
        Log.d(TAG, "init")
    }

    override fun processEvent(event: UpdateMyTvScreenEvent) {
        when (event) {
            is UpdateMyTvScreenEvent.OnEpisodeChanged -> onEpisodeChanged(event)
            is UpdateMyTvScreenEvent.OnSeasonChanged -> onSeasonChanged(event)
            UpdateMyTvScreenEvent.OnSubmit -> onSubmit()
            is UpdateMyTvScreenEvent.OnLastWatchedDateChanged -> onLastWatchedDateChanged(event.time)
            UpdateMyTvScreenEvent.OnLastWatchedDatePickerCloseRequest -> onLastWatchedDatePickerCloseRequest()
            UpdateMyTvScreenEvent.OnLastWatchedDatePickerOpenRequest -> onLastWatchedDatePickerOpenRequest()
        }
    }

    private fun onLastWatchedDateChanged(time: Long?) {
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(
                updateTvData = currentUpdateTvData?.copy(
                    lastWatchedTime = time,
                    lastWatchedTimeUIText = TextFieldValue(
                        dateTimeUtils.getDateFromMillis(time) ?: ""
                    )
                ),
                // TODO: P8 Currently this is how we are handling
                isLastWatchedDatePickerOpened = false,
            )
        }
    }

    private fun onLastWatchedDatePickerCloseRequest() {

        setState {
            copy(
                isLastWatchedDatePickerOpened = false
            )
        }
    }

    private fun onLastWatchedDatePickerOpenRequest() {
        setState {
            copy(
                isLastWatchedDatePickerOpened = true
            )
        }
    }

    fun setData(data: UpdateMyTvUIStateActual.UpdateTvData) {
        setState {
            copy(
                updateTvData = data
            )
        }
    }

    private fun onSubmit() {
        viewModelScope.launch {
            // TODO: Not the case expecting. Handle/Check this
            val data = state.updateTvData ?: return@launch

            setState {
                copy(
                    isLoading = true
                )
            }

            updateMyTvUseCase(
                myTvDomainModel = MyTvDomainModel(
                    id = data.id,
                    name = data.name,
                    lastWatchedSeason = data.lastWatchedSeason.text.toIntOrNull(),
                    lastWatchedEpisode = data.lastWatchedEpisode.text.toIntOrNull(),
                    lastWatchedTime = data.lastWatchedTime,
                    // TODO: Should not update image source
                    imgSource = data.imgSource?.data as? String
                )
            )
            setState {
                copy(
                    isLoading = false,
                    isClosed = true
                )
            }
        }
    }

    private fun onSeasonChanged(event: UpdateMyTvScreenEvent.OnSeasonChanged) {
        val lastWatchedSeason = event.textFieldValue
        if (lastWatchedSeason.text.length > 2) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData?.copy(lastWatchedSeason = lastWatchedSeason))
        }
    }

    private fun onEpisodeChanged(event: UpdateMyTvScreenEvent.OnEpisodeChanged) {
        val lastWatchedEpisode = event.textFieldValue
        if (lastWatchedEpisode.text.length > 4) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData?.copy(lastWatchedEpisode = lastWatchedEpisode))
        }
    }

    companion object {
        private const val TAG = "UpdateMyTvViewModel"
    }


}