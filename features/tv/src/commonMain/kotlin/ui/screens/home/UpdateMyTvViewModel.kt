package com.hrudhaykanth116.tv.ui.screens.home

import androidx.compose.ui.text.input.TextFieldValue
import com.hrudhaykanth116.core.common.utils.log.Logger
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.ui.viewmodels.UDFViewModel
import com.hrudhaykanth116.tv.domain.usecases.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.domain.models.MyTv
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEffect
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import kotlinx.coroutines.launch

class UpdateMyTvViewModel(
    // updateTvData: UpdateMyTvUIStateActual.UpdateTvData,
    private val updateMyTvUseCase: UpdateMyTvUseCase,
    private val dateTimeUtils: DateTimeUtils,
) : UDFViewModel<UpdateMyTvUIStateActual, UpdateMyTvScreenEvent, UpdateMyTvScreenEffect>(
    UpdateMyTvUIStateActual()
) {

    init {
        Logger.d(TAG, "init")
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
                        dateTimeUtils.getFormattedDateTime(time, "dd/MM/yyyy") ?: ""
                    )
                ),
                // hrudhay_check_list: P8 Currently this is how we are handling
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
            // hrudhay_check_list: Not the case expecting. Handle/Check this
            val data = state.updateTvData ?: return@launch

            setState {
                copy(
                    isLoading = true
                )
            }

            updateMyTvUseCase(
                myTv = MyTv(
                    id = data.id,
                    name = data.name,
                    lastWatchedSeason = data.lastWatchedSeason.text.toIntOrNull(),
                    lastWatchedEpisode = data.lastWatchedEpisode.text.toIntOrNull(),
                    lastWatchedTime = data.lastWatchedTime,
                    // hrudhay_check_list: Should not update image source
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