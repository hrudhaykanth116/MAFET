package com.hrudhaykanth116.media.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.media.domain.usecases.SearchMediaUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaSearchViewModel(
    private val searchMediaUseCase: SearchMediaUseCase,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<MediaSearchUIState, MediaSearchEvent, MediaSearchEffect>(
    initialState = UIState.Idle(MediaSearchUIState()),
    defaultState = MediaSearchUIState(),
    networkMonitor = networkMonitor
) {

    private var searchJob: Job? = null
    private val recentSearchesCache = mutableListOf<String>()

    override fun initializeData() {
        loadRecentSearches()
    }

    override fun processEvent(event: MediaSearchEvent) {
        when (event) {
            is MediaSearchEvent.OnQueryChange -> handleQueryChange(event.query)
            is MediaSearchEvent.OnSearch -> handleSearch(event.query)
            is MediaSearchEvent.OnRecentSearchClick -> handleSearch(event.query)
            is MediaSearchEvent.OnTrendingSearchClick -> handleSearch(event.query)
            is MediaSearchEvent.OnClearQuery -> handleClearQuery()
            is MediaSearchEvent.OnBackClick -> setEffect(MediaSearchEffect.NavigateBack)
            is MediaSearchEvent.ToggleMediaType -> handleToggleMediaType(event.type)
            is MediaSearchEvent.ApplyFilters -> handleApplyFilters(event.filters)
            is MediaSearchEvent.OnMediaItemClick -> handleMediaItemClick(event.item)
            is MediaSearchEvent.LoadNextPage -> loadNextPage()
        }
    }

    private fun loadRecentSearches() {
        setIdleState {
            copy(recentSearches = recentSearchesCache.take(5))
        }
    }

    private fun handleQueryChange(query: String) {
        setIdleState {
            copy(query = query, showSuggestions = query.isBlank())
        }

        if (query.length >= 2) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(dispatcher) {
                delay(500)
                performSearch(query, page = 1, isNewSearch = true)
            }
        } else if (query.isBlank()) {
            setIdleState {
                copy(items = emptyList(), showSuggestions = true)
            }
        }
    }

    private fun handleSearch(query: String) {
        if (query.isBlank()) return

        setIdleState {
            copy(query = query, showSuggestions = false)
        }

        addToRecentSearches(query)
        performSearch(query, page = 1, isNewSearch = true)
    }

    private fun performSearch(query: String, page: Int, isNewSearch: Boolean) {
        if (contentStateOrDefault.isSearching || contentStateOrDefault.isLoadingMore) return

        viewModelScope.launch(dispatcher) {
            if (page == 1 && isNewSearch) {
                setIdleState {
                    copy(isSearching = true, items = emptyList())
                }
            } else {
                setIdleState {
                    copy(isLoadingMore = true)
                }
            }

            val currentState = contentStateOrDefault
            val result = searchMediaUseCase(
                query = query,
                mediaType = currentState.mediaType,
                page = page,
                perPage = 20,
                filters = currentState.filters
            )

            when (result) {
                is DomainResult.Success -> {
                    val newItems = if (isNewSearch || page == 1) {
                        result.data
                    } else {
                        currentState.items + result.data
                    }

                    setIdleState {
                        copy(
                            items = newItems,
                            currentPage = page,
                            isSearching = false,
                            isLoadingMore = false,
                            canLoadMore = result.data.isNotEmpty()
                        )
                    }
                }

                is DomainResult.Error -> {
                    setIdleState {
                        copy(isSearching = false, isLoadingMore = false)
                    }
                    showUserMessage(result.error.toMessage().toUIText().toErrorMessage())
                }
            }
        }
    }

    private fun loadNextPage() {
        val currentState = contentStateOrDefault
        if (currentState.canLoadMore && !currentState.isLoadingMore && currentState.query.isNotBlank()) {
            performSearch(
                query = currentState.query,
                page = currentState.currentPage + 1,
                isNewSearch = false
            )
        }
    }

    private fun handleClearQuery() {
        searchJob?.cancel()
        setIdleState {
            copy(
                query = "",
                items = emptyList(),
                showSuggestions = true,
                currentPage = 1,
                canLoadMore = true
            )
        }
    }

    private fun handleToggleMediaType(type: com.hrudhaykanth116.media.domain.models.MediaType) {
        setIdleState {
            copy(mediaType = type, items = emptyList(), currentPage = 1, canLoadMore = true)
        }

        val query = contentStateOrDefault.query
        if (query.isNotBlank()) {
            performSearch(query, page = 1, isNewSearch = true)
        }
    }

    private fun handleApplyFilters(newFilters: com.hrudhaykanth116.media.domain.models.FilterState) {
        setIdleState {
            copy(filters = newFilters, items = emptyList(), currentPage = 1, canLoadMore = true)
        }

        val query = contentStateOrDefault.query
        if (query.isNotBlank()) {
            performSearch(query, page = 1, isNewSearch = true)
        }
    }

    private fun handleMediaItemClick(item: com.hrudhaykanth116.media.domain.models.MediaItem) {
        setEffect(MediaSearchEffect.NavigateToDetail(item.id, item.type))
    }

    private fun addToRecentSearches(query: String) {
        if (query.isBlank()) return

        recentSearchesCache.remove(query)
        recentSearchesCache.add(0, query)

        if (recentSearchesCache.size > 10) {
            recentSearchesCache.removeAt(recentSearchesCache.size - 1)
        }

        setIdleState {
            copy(recentSearches = recentSearchesCache.take(5))
        }
    }
}
