package com.hrudhaykanth116.tv.ui.models.home

sealed interface TvHomeScreenEvent {
    data class Delete(val id: Int): TvHomeScreenEvent
    data class MyTvListItemClicked(val myTv: MyTvUIState): TvHomeScreenEvent
    object CloseUpdateTv: TvHomeScreenEvent
}