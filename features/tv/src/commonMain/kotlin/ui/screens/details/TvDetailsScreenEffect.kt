package com.hrudhaykanth116.tv.ui.screens.details

sealed interface TvDetailsScreenEffect {
    data class NavigateToTvDetails(val id: Int) : TvDetailsScreenEffect
    data class OpenVideoUrl(val url: String) : TvDetailsScreenEffect
}
