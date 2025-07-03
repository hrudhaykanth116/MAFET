package com.hrudhaykanth116.media.ui.screens

sealed interface MediaScreenEvent {

    data class OnSearchTextChanged(val text: String)

}