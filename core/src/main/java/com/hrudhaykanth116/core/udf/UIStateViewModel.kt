package com.hrudhaykanth116.core.udf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.network.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UIStateViewModel<STATE, EVENT, EFFECT>(
    initialState: UIState<STATE>,
    val defaultState: STATE,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {

    protected var timeHiddenToast: Long = 3000L

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow: StateFlow<UIState<STATE>> = _uiStateFlow.asStateFlow()
        // .onStart {
        //     onRetry()
        // }.stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    // Currently using Shared flow which could cause effect lose if collector is paused.
    // These are typically viewModel events. User events can be handled within UI.
    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    val uiState: UIState<STATE> get() = uiStateFlow.value

    val contentStateFlow: Flow<STATE> = _uiStateFlow.map {
        it.contentState ?: defaultState
    }

    val currentContentState: STATE? get() = uiState.contentState
    val contentStateOrDefault: STATE get() = uiState.contentState ?: defaultState

    abstract fun initializeData() // Initial data if any. Should be called once when screen is launched when data is needed to be fetched
    abstract fun processEvent(event: EVENT)

    fun onUserMessageShown(idleUIState: UIState.Idle<STATE>) {
        setState {
            UIState.Idle(
                contentState = idleUIState.contentState,
                userMessage = null
            )
        }
    }

    fun isNetworkAvailable(): Boolean {
        return networkMonitor.isNetworkAvailable()
    }

    fun onRetry() {
        initializeData()
    }

    // Prevent setting newState. Always use copy to avoid wrong state being set when done in parallel.
    protected fun setState(newState: UIState<STATE>.() -> UIState<STATE>) {
        _uiStateFlow.update(newState)
    }

    // protected fun updateContentState(contentState: STATE.() -> STATE) {
    //     _uiStateFlow.update {
    //         it.copyUIState(newContentState = contentStateOrDefault.contentState())
    //     }
    // }

    protected fun setLoadingState(contentSTATE: STATE? = null) {
        setState {
            UIState.Loading(contentSTATE)
        }
    }

    protected fun setIdleState(contentSTATE: STATE? = null) {
        setState {
            UIState.Idle(contentSTATE)
        }
    }

    protected fun setIdleState(contentState: STATE.() -> STATE) {
        setState {
            UIState.Idle(
                contentStateOrDefault.contentState()
            )
        }
    }

    protected fun setEffect(newEffect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    protected fun getCurrentState(): UIState<STATE> {
        return uiStateFlow.value
    }

    // protected fun showToastMessage(message: UserMessage) {
    //     viewModelScope.launch {
    //         setState {
    //             copyUIState(
    //                 newUserMessage = message
    //             )
    //         }
    //         delay(timeHiddenToast)
    //         hideToastMessage()
    //     }
    // }

    // fun hideToastMessage() {
    //     setState {
    //         copyUIState(
    //             newUserMessage = null
    //         )
    //     }
    // }

    // fun <T> callMultiApiOnThread(
    //     requests: MutableList<Flow<ApiResultWrapper<T>>>,
    //     apiSuccess: (T) -> Unit,
    //     apiError: (ApiError) -> Unit = {},
    //     onDoneCallApi: () -> Unit = {}
    // ) {
    //     viewModelScope.launch {
    //         try {
    //             requests.map {
    //                 async {
    //                     it.collect { result ->
    //                         Log.e(TAG, "callAPIOnThread: $result")
    //                         when (result) {
    //                             is ApiResultWrapper.Success -> {
    //                                 apiSuccess(result.value)
    //                             }
    //
    //                             is ApiResultWrapper.Error -> {
    //                                 apiError(result.apiError)
    //                             }
    //                         }
    //                     }
    //                 }
    //             }.awaitAll()
    //         } catch (exception: Exception) {
    //             Log.e(TAG, "callMultiApiOnThread: ", exception)
    //             apiError(ApiError.CustomError("Something went wrong".toUIText()))
    //         }
    //
    //         onDoneCallApi()
    //     }
    // }

    companion object {
        private const val TAG = "StatefulViewModel"
    }

}
