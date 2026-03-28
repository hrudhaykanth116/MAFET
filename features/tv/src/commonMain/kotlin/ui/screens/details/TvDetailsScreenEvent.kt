package com.hrudhaykanth116.tv.ui.screens.details

sealed interface TvDetailsScreenEvent {

    data class OnAddClicked(val id: Int) : TvDetailsScreenEvent

}