package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.mappers.mapToUIMessage
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.domaintemp.AddMyTvUseCase
import com.hrudhaykanth116.tv.domaintemp.GetTvDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
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

    override fun initializeData() {
        // fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {

            val tvDetailsUseCase: RepoResultWrapper<TvShowDetails> = getTvDetailsUseCase(id)
            when (tvDetailsUseCase) {
                is RepoResultWrapper.Error -> {
                    setState { UIState.Error(
                        contentState = contentState,
                        errorState = tvDetailsUseCase.errorState
                    ) }
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
            val result = addMyTvUseCase(event.id)

            when (result) {
                is RepoResultWrapper.Error -> {
                    setState {
                        UIState.Idle(
                            contentState = contentState,
                            userMessage = result.errorState.mapToUIMessage(),
                        )
                    }
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