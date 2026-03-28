package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.models.UserMessage
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.data.RepoResultWrapper
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.domaintemp.AddMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetTvDetailsUseCase
import kotlinx.coroutines.launch

class TvDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val addMyTvUseCase: AddMyTvUseCase,
    private val networkMonitor: NetworkMonitor,
) : UIStateViewModel<TvDetailsScreenUIState, TvDetailsScreenEvent, TvDetailsScreenEffect>(
    initialState = UIState.Loading(),
    defaultState = TvDetailsScreenUIState(),
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

            val tvDetailsUseCase: RepoResultWrapper<TvShowDetails> = getTvDetailsUseCase(id)
            when (tvDetailsUseCase) {
                is RepoResultWrapper.Error -> {
                    setState {
                        UIState.Error(
                            contentState = contentState,
                            errorState = tvDetailsUseCase.errorState
                        )
                    }
                }

                is RepoResultWrapper.Success -> {
                    setState {
                        UIState.Idle(
                            TvDetailsScreenUIState(
                                tvShowDetails = tvDetailsUseCase.data
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
                is RepoResultWrapper.Error -> {
                    // TODO: kmp do this
                    // setState {
                    //     UIState.Idle(
                    //         contentState = contentState,
                    //         userMessage = result.errorState.mapToUIMessage(),
                    //     )
                    // }
                }

                is RepoResultWrapper.Success -> {
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