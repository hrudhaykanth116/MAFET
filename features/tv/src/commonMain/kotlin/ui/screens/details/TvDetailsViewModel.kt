package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.domain.constants.BaseUrlConstants
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.repository.ITvShowsRepository
import com.hrudhaykanth116.tv.domain.usecases.AddMyTvUseCase
import com.hrudhaykanth116.tv.domain.usecases.DeleteMyTvUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetTvDetailsUseCase
import com.hrudhaykanth116.tv.domain.usecases.TvDetailsResult
import com.hrudhaykanth116.tv.ui.mappers.toAboutTabUIState
import com.hrudhaykanth116.tv.ui.mappers.toCastUIStates
import com.hrudhaykanth116.tv.ui.mappers.toMediaTabUIState
import com.hrudhaykanth116.tv.ui.mappers.toMoreLikeThisTabUIState
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class TvDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val addMyTvUseCase: AddMyTvUseCase,
    private val deleteMyTvUseCase: DeleteMyTvUseCase,
    private val networkMonitor: NetworkMonitor,
    private val tvShowsRepository: ITvShowsRepository,
) : UIStateViewModel<TvDetailsScreenUIState, TvDetailsScreenEvent, TvDetailsScreenEffect>(
    initialState = UIState.Loading(),
    defaultState = TvDetailsScreenUIState(
        id = -1,
        title = "",
        overview = "",
        backdropImage = null,
        dateRange = "",
        rating = "",
        genres = emptyList(),
        networks = emptyList(),
    ),
    networkMonitor = networkMonitor,
) {

    val id: Int = checkNotNull(savedStateHandle["id"]) {
        "ViewModel requires an id argument"
    }

    private var tvShowDetail: TvShowDetail? = null

    init {
        initializeData()
    }

    override fun initializeData() {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            setState { UIState.Loading(currentContentState) }

            val result: DomainResult<TvDetailsResult> = getTvDetailsUseCase(id)
            when (result) {
                is DomainResult.Error -> {
                    setState { UIState.Error(errorState = result.error) }
                }

                is DomainResult.Success -> {
                    tvShowDetail = result.data.tvShowDetail
                    setState {
                        UIState.Idle(
                            result.data.tvShowDetail.toUIState(
                                isBookmarked = result.data.isBookmarked,
                            )
                        )
                    }
                    fetchCredits()
                }
            }
        }
    }

    private fun fetchCredits() {
        viewModelScope.launch {
            val result = tvShowsRepository.getTvCredits(id)
            if (result is DomainResult.Success) {
                val castStates = result.data.toCastUIStates()
                val detail = tvShowDetail ?: return@launch
                setIdleState {
                    copy(aboutTabState = detail.toAboutTabUIState(cast = castStates))
                }
            }
        }
    }

    private fun fetchMoreLikeThis() {
        viewModelScope.launch {
            val result = tvShowsRepository.getTvShowsSimilar(id, 1)
            if (result is DomainResult.Success) {
                setIdleState {
                    copy(moreLikeThisTabState = result.data.toMoreLikeThisTabUIState())
                }
            }
        }
    }

    private fun fetchMedia() {
        viewModelScope.launch {
            coroutineScope {
                val imagesDeferred = async { tvShowsRepository.getTvImages(id) }
                val videosDeferred = async { tvShowsRepository.getTvShowVideos(id) }

                val imagesResult = imagesDeferred.await()
                val videosResult = videosDeferred.await()

                if (imagesResult is DomainResult.Success && videosResult is DomainResult.Success) {
                    setIdleState {
                        copy(
                            mediaTabState = toMediaTabUIState(
                                images = imagesResult.data,
                                videos = videosResult.data,
                            )
                        )
                    }
                }
            }
        }
    }

    override fun processEvent(event: TvDetailsScreenEvent) {
        when (event) {
            is TvDetailsScreenEvent.OnAddClicked -> {
                viewModelScope.launch { onAddClicked(event) }
            }

            is TvDetailsScreenEvent.OnTabSelected -> {
                when (event.tabIndex) {
                    0 -> if (contentStateOrDefault.aboutTabState == null) fetchCredits()
                    1 -> if (contentStateOrDefault.moreLikeThisTabState == null) fetchMoreLikeThis()
                    2 -> if (contentStateOrDefault.mediaTabState == null) fetchMedia()
                }
            }

            is TvDetailsScreenEvent.OnSimilarShowClicked -> {
                setEffect(TvDetailsScreenEffect.NavigateToTvDetails(event.id))
            }

            is TvDetailsScreenEvent.OnVideoClicked -> {
                val url = when {
                    event.site.equals("YouTube", ignoreCase = true) ->
                        "${BaseUrlConstants.YOUTUBE_BASE_URL}${event.key}"
                    event.site.equals("Vimeo", ignoreCase = true) ->
                        "${BaseUrlConstants.VIMEO_BASE_URL}${event.key}"
                    else -> return
                }
                setEffect(TvDetailsScreenEffect.OpenVideoUrl(url))
            }
        }
    }

    private fun onAddClicked(event: TvDetailsScreenEvent.OnAddClicked) {
        viewModelScope.launch {
            val currentlyBookmarked = contentStateOrDefault.isBookmarked
            setState { UIState.Loading(contentState) }

            if (currentlyBookmarked) {
                when (val result = deleteMyTvUseCase(event.id)) {
                    is DomainResult.Error -> {
                        setState {
                            UIState.Error(
                                contentState = contentState,
                                errorState = result.error
                            )
                        }
                    }

                    is DomainResult.Success -> {
                        setState {
                            UIState.Idle(
                                contentState = contentStateOrDefault.copy(isBookmarked = false),
                                userMessage = UserMessage.Success(message = "Removed from Your List".toUIText()),
                            )
                        }
                    }
                }
            } else {
                when (val result = addMyTvUseCase(event.id)) {
                    is DomainResult.Error -> {
                        setState {
                            UIState.Error(
                                contentState = contentState,
                                errorState = result.error
                            )
                        }
                    }

                    is DomainResult.Success -> {
                        setState {
                            UIState.Idle(
                                contentState = contentStateOrDefault.copy(isBookmarked = true),
                                userMessage = UserMessage.Success(message = "Added to Your List".toUIText()),
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TvDetailsViewModel"
    }
}
