package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.domain.models.TvShowDetail
import com.hrudhaykanth116.tv.domain.usecases.AddMyTvUseCase
import com.hrudhaykanth116.tv.domain.usecases.GetTvDetailsUseCase
import com.hrudhaykanth116.tv.ui.mappers.toUIState
import kotlinx.coroutines.launch

class TvDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val addMyTvUseCase: AddMyTvUseCase,
    private val networkMonitor: NetworkMonitor,
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

    init {
        initializeData()
    }

    override fun initializeData() {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {

            setState {
                UIState.Loading(
                    currentContentState
                )
            }

            val tvDetailsUseCase: DomainResult<TvShowDetail> = getTvDetailsUseCase(id)
            when (tvDetailsUseCase) {
                is DomainResult.Error -> {
                    setState {
                        UIState.Error(
                            errorState = tvDetailsUseCase.error
                        )
                    }
                }

                is DomainResult.Success -> {
                    setState {
                        UIState.Idle(
                            tvDetailsUseCase.data.toUIState()
                        )
                    }
                }
            }
        }
    }

    override fun processEvent(event: TvDetailsScreenEvent) {
        when (event) {
            is TvDetailsScreenEvent.OnAddClicked -> {
                viewModelScope.launch {
                    onAddClicked(event)
                }
            }
        }
    }

    private fun onAddClicked(event: TvDetailsScreenEvent.OnAddClicked) {
        viewModelScope.launch {

            setState {
                UIState.Loading(
                    contentState
                )
            }

            val result = addMyTvUseCase(event.id)

            when (result) {
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
                            contentState = contentState,
                            userMessage = UserMessage.Success(message = "Added to Your List".toUIText()),
                        )
                    }
                }
            }

        }
    }

    companion object {
        private const val TAG = "TvDetailsViewModel"
    }

}