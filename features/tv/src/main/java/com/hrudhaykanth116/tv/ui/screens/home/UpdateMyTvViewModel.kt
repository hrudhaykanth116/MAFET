package com.hrudhaykanth116.tv.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.domaintemp.UpdateMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEffect
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMyTvViewModel @Inject constructor(
    // updateTvData: UpdateMyTvUIStateActual.UpdateTvData,
    private val updateMyTvUseCase: UpdateMyTvUseCase,
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
        }
    }

    fun setData(data: UpdateMyTvUIStateActual.UpdateTvData){
        setState {
            copy(
                updateTvData = data
            )
        }
    }

    private fun onSubmit(){
        viewModelScope.launch{
            // TODO: Not the case expecting. Handle/Check this
            val data = state.updateTvData ?: return@launch

            setState {
                copy(
                    isLoading = true
                )
            }

            // TODO: Stimulating long job.
            delay(3000)

            updateMyTvUseCase(
                myTvDomainModel = MyTvDomainModel(
                    id = data.id,
                    name = data.name,
                    lastWatchedSeason = data.lastWatchedSeason.text.toIntOrNull(),
                    lastWatchedEpisode = data.lastWatchedEpisode.text.toIntOrNull(),
                    // TODO: Implement time
                    lastWatchedTime = System.currentTimeMillis(),
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
        if(lastWatchedSeason.text.length > 2) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData?.copy(lastWatchedSeason = lastWatchedSeason))
        }
    }

    private fun onEpisodeChanged(event: UpdateMyTvScreenEvent.OnEpisodeChanged) {
        val lastWatchedEpisode = event.textFieldValue
        if(lastWatchedEpisode.text.length > 4) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData?.copy(lastWatchedEpisode = lastWatchedEpisode))
        }
    }

    companion object{
        private const val TAG = "UpdateMyTvViewModel"
    }


}