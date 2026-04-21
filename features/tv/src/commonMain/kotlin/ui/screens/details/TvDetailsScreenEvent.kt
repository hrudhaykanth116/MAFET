package com.hrudhaykanth116.tv.ui.screens.details

sealed interface TvDetailsScreenEvent {
    data class OnAddClicked(val id: Int) : TvDetailsScreenEvent
    data class OnTabSelected(val tabIndex: Int) : TvDetailsScreenEvent
    data class OnSimilarShowClicked(val id: Int) : TvDetailsScreenEvent
    data class OnVideoClicked(val key: String, val site: String) : TvDetailsScreenEvent
    data class OnImageClick(val url: String) : TvDetailsScreenEvent
    data object OnCloseFullscreen : TvDetailsScreenEvent
    data class OnDownloadImage(val url: String) : TvDetailsScreenEvent
}
