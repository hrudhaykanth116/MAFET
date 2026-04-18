package com.hrudhaykanth116.tv.ui.screens

sealed interface TvShowsScreenEvent {

    data class OnItemClicked(val id: Int) : TvShowsScreenEvent

}