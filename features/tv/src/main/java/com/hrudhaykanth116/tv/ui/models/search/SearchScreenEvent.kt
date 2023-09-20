package com.hrudhaykanth116.tv.ui.models.search

sealed interface SearchScreenEvent {

    data class OnSearchTextChanged(val searchText: String) : SearchScreenEvent
    object OnSearchIconClicked : SearchScreenEvent

}