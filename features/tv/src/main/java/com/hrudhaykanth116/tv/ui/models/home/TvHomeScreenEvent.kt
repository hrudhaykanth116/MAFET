package com.hrudhaykanth116.tv.ui.models.home

sealed interface TvHomeScreenEvent {
    object AddNew: TvHomeScreenEvent
    data class Delete(val id: String): TvHomeScreenEvent
    data class MyTvListItemClicked(val id: String): TvHomeScreenEvent
}