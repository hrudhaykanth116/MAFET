package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.ui.models.UserMessage
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
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

    init {

        fetchData()

    }

    private fun fetchData() {
        viewModelScope.launch {

            val tvDetailsUseCase: DataResult<TvShowDetails> = getTvDetailsUseCase(id)
            when (tvDetailsUseCase) {
                is DataResult.Error -> {
                    setState { UIState.Error("Something went wrong".toUIText()) }
                }

                is DataResult.Success -> {
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
                is DataResult.Error -> {
                    setState {
                        copyUIState(
                            newUserMessage = UserMessage.Error(message = result.uiMessage),
                        )
                    }
                }

                is DataResult.Success -> {
                    setState {
                        copyUIState(
                            newUserMessage = UserMessage.Success(message = "Added to Your List".toUIText()),
                        )
                    }
                }
            }

        }
    }

    override fun onRetry() {

    }

    companion object {
        private const val TAG = "TvDetailsViewModel"
    }

}