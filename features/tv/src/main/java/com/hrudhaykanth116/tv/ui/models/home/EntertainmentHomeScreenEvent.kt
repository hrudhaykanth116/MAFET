package com.hrudhaykanth116.tv.ui.models.home

sealed interface EntertainmentHomeScreenEvent {
    data class Delete(val id: Int): EntertainmentHomeScreenEvent
    data class MyEntertainmentListItemClicked(val myTv: MyTvUIState): EntertainmentHomeScreenEvent
    object CloseUpdateTv: EntertainmentHomeScreenEvent
}