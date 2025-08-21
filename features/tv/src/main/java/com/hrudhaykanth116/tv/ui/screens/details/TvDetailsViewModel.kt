package com.hrudhaykanth116.tv.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.tv.data.datasources.remote.models.TvShowDetails
import com.hrudhaykanth116.tv.domaintemp.GetTvDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
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

            else -> {

            }
        }
    }

    override fun onRetry() {

    }

    companion object {
        private const val TAG = "TvDetailsViewModel"
    }

}