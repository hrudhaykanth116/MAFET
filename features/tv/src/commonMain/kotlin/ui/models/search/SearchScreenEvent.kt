package com.hrudhaykanth116.tv.ui.models.search

sealed interface SearchScreenEvent {

    data class OnSearchTextChanged(val searchText: String) : SearchScreenEvent
    data class OnAddClicked(val id: Int) : SearchScreenEvent
    object OnSearchIconClicked : SearchScreenEvent

}