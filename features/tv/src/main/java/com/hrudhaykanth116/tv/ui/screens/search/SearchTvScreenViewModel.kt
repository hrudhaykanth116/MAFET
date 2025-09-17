package com.hrudhaykanth116.tv.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.mappers.mapToUIMessage
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.data.repositories.tv.MyTvListRepository
import com.hrudhaykanth116.tv.domaintemp.AddMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetTvListByQuery
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenEffect
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenEvent
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenItemUIState
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchTvScreenViewModel @Inject constructor(
    private val getTvListByQuery: GetTvListByQuery,
    private val addMyTvUseCase: AddMyTvUseCase,
    private val myTvListRepository: MyTvListRepository,

    ) : UDFViewModel<SearchScreenState, SearchScreenEvent, SearchScreenEffect>(
    SearchScreenState("")
) {

    private var searchTvJob: Job? = null

    val data = myTvListRepository.observeMyTvList()

    init {

        viewModelScope.launch {
            stateFlow.mapLatest {
                it.query
            }.distinctUntilChanged().collectLatest {
                fetchTvList(it)
            }
        }


        // hrudhay_check_list: P4 Optimise this based on requirement
        viewModelScope.launch(Dispatchers.Default) {

            data.collectLatest { myTvEntityList ->
                val searchResultsWithUpdatedIsMyTv: List<SearchScreenItemUIState> = state.searchResults.map { searchScreenItemUIState ->
                    searchScreenItemUIState.copy(
                        isMyTvList = myTvEntityList.any { it.id == searchScreenItemUIState.id }
                    )
                }
                setState {
                    copy(
                        searchResults = searchResultsWithUpdatedIsMyTv
                    )
                }
            }
        }

    }

    private fun fetchTvList(it: String) {

        searchTvJob?.cancel()

        if (it.isEmpty()) {
            // hrudhay_check_list: Show top tv/popular/suggested shows
            setState {
                copy(
                    searchResults = listOf(),
                    userMessage = null,
                    isLoading = false,
                )
            }
            return
        }

        searchTvJob = viewModelScope.launch(Dispatchers.Default) {

            // Delay to wait for new character addition to the search query.
            delay(500)

            setState {
                copy(
                    isLoading = true
                )
            }

            val result: RepoResultWrapper<List<SearchScreenItemUIState>?> = getTvListByQuery(it)

            when (result) {
                is RepoResultWrapper.Error -> {
                    setState {
                        copy(
                            userMessage = result.errorState.mapToUIMessage(),
                            isLoading = false,
                        )
                    }
                }
                is RepoResultWrapper.Success -> {
                    setState {
                        copy(
                            searchResults = result.data ?: emptyList(),
                            userMessage = null,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    override fun processEvent(event: SearchScreenEvent) {

        when (event) {
            SearchScreenEvent.OnSearchIconClicked -> {
                fetchTvList(state.query)
            }

            is SearchScreenEvent.OnSearchTextChanged -> {
                onSearchTextChanged(event.searchText)
            }

            is SearchScreenEvent.OnAddClicked -> {
                onAddClicked(event)
            }
        }
    }

    private fun onAddClicked(event: SearchScreenEvent.OnAddClicked) {
        viewModelScope.launch {
            val result = addMyTvUseCase(event.id)

            when (result) {
                is RepoResultWrapper.Error -> {
                    setState {
                        copy(
                            userMessage = result.errorState.mapToUIMessage(),
                        )
                    }
                }

                is RepoResultWrapper.Success -> {
                    setState {
                        copy(
                            userMessage = null,
                        )
                    }
                }
            }

        }
    }

    private fun onSearchTextChanged(searchText: String) {
        setState {
            copy(
                query = searchText
            )
        }
    }


}