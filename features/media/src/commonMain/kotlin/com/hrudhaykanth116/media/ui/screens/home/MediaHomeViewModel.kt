package com.hrudhaykanth116.media.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.models.OrientationType
import com.hrudhaykanth116.media.domain.usecases.GetCuratedMediaUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MediaHomeViewModel(
    private val getCuratedMediaUseCase: GetCuratedMediaUseCase,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher
) : UIStateViewModel<MediaHomeUIState, MediaHomeEvent, MediaHomeEffect>(
    initialState = UIState.Idle(MediaHomeUIState()),
    defaultState = MediaHomeUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        loadMedia(page = 1, isRefresh = false)
    }

    override fun processEvent(event: MediaHomeEvent) {
        when (event) {
            is MediaHomeEvent.LoadNextPage -> loadNextPage()
            is MediaHomeEvent.Refresh -> refresh()
            is MediaHomeEvent.ToggleMediaType -> handleToggleMediaType(event.type)
            is MediaHomeEvent.FilterOrientation -> handleFilterOrientation(event.orientation)
            is MediaHomeEvent.ApplyFilters -> handleApplyFilters(event.filters)
            is MediaHomeEvent.OnMediaItemClick -> handleMediaItemClick(event.item)
            is MediaHomeEvent.OpenFilters -> setEffect(MediaHomeEffect.ShowFilters)
            is MediaHomeEvent.OpenSearch -> setEffect(MediaHomeEffect.NavigateToSearch)
        }
    }

    private fun loadMedia(page: Int, isRefresh: Boolean) {
        if (!isRefresh && contentStateOrDefault.isLoadingMore) return

        viewModelScope.launch(dispatcher) {
            if (page == 1 && !isRefresh) {
                setLoadingState(contentStateOrDefault)
            } else {
                setIdleState {
                    copy(isLoadingMore = true)
                }
            }

            val currentState = contentStateOrDefault
            val result = getCuratedMediaUseCase(
                mediaType = currentState.mediaType,
                page = page,
                perPage = 20,
                filters = currentState.filters
            )

            when (result) {
                is DomainResult.Success -> {
                    val newItems = if (isRefresh || page == 1) {
                        result.data
                    } else {
                        currentState.items + result.data
                    }

                    setIdleState {
                        copy(
                            items = newItems,
                            currentPage = page,
                            isLoadingMore = false,
                            canLoadMore = result.data.isNotEmpty()
                        )
                    }
                }

                is DomainResult.Error -> {
                    if (page == 1) {
                        setState {
                            UIState.Error(
                                errorState = result.error,
                                contentState = currentState
                            )
                        }
                    } else {
                        setIdleState {
                            copy(isLoadingMore = false)
                        }
                        showUserMessage(result.error.toMessage().toUIText().toErrorMessage())
                    }
                }
            }
        }
    }

    private fun loadNextPage() {
        val currentState = contentStateOrDefault
        if (currentState.canLoadMore && !currentState.isLoadingMore) {
            loadMedia(page = currentState.currentPage + 1, isRefresh = false)
        }
    }

    private fun refresh() {
        loadMedia(page = 1, isRefresh = true)
    }

    private fun handleToggleMediaType(type: MediaType) {
        setIdleState {
            copy(
                mediaType = type,
                items = emptyList(),
                currentPage = 1,
                canLoadMore = true
            )
        }
        loadMedia(page = 1, isRefresh = false)
    }

    private fun handleFilterOrientation(orientation: OrientationType) {
        setIdleState {
            copy(
                orientation = orientation,
                filters = filters.copy(orientation = orientation),
                items = emptyList(),
                currentPage = 1,
                canLoadMore = true
            )
        }
        loadMedia(page = 1, isRefresh = false)
    }

    private fun handleApplyFilters(newFilters: com.hrudhaykanth116.media.domain.models.FilterState) {
        setIdleState {
            copy(
                filters = newFilters,
                orientation = newFilters.orientation,
                items = emptyList(),
                currentPage = 1,
                canLoadMore = true
            )
        }
        loadMedia(page = 1, isRefresh = false)
    }

    private fun handleMediaItemClick(item: com.hrudhaykanth116.media.domain.models.MediaItem) {
        setEffect(MediaHomeEffect.NavigateToDetail(item.id, item.type))
    }
}
