package com.hrudhaykanth116.tv.ui.screens.home

import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEffect
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvScreenEvent
import com.hrudhaykanth116.tv.ui.models.updatemytv.UpdateMyTvUIStateActual

class UpdateMyTvViewModel constructor(
    updateTvData: UpdateMyTvUIStateActual.UpdateTvData,
) : UDFViewModel<UpdateMyTvUIStateActual, UpdateMyTvScreenEvent, UpdateMyTvScreenEffect>(
    UpdateMyTvUIStateActual(updateTvData)
) {

    override fun processEvent(event: UpdateMyTvScreenEvent) {
        when (event) {
            is UpdateMyTvScreenEvent.OnEpisodeChanged -> {
                onEpisodeChanged(event)
            }
            is UpdateMyTvScreenEvent.OnSeasonChanged -> {
                onSeasonChanged(event)
            }
        }
    }

    private fun onSeasonChanged(event: UpdateMyTvScreenEvent.OnSeasonChanged) {
        val lastWatchedSeason = event.textFieldValue
        if(lastWatchedSeason.text.length > 2) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData.copy(lastWatchedSeason = lastWatchedSeason))
        }
    }

    private fun onEpisodeChanged(event: UpdateMyTvScreenEvent.OnEpisodeChanged) {
        val lastWatchedEpisode = event.textFieldValue
        if(lastWatchedEpisode.text.length > 4) return
        val currentUpdateTvData = state.updateTvData
        setState {
            copy(updateTvData = currentUpdateTvData.copy(lastWatchedEpisode = lastWatchedEpisode))
        }
    }


}