package com.hrudhaykanth116.tv.ui.screens

sealed interface PopularTvScreenEvent {

    data class OnItemClicked(val id: Int) : PopularTvScreenEvent

}