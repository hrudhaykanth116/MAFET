package com.hrudhaykanth116.core.udf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.models.UIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UIStateViewModel<STATE, EVENT, EFFECT>(
    initialState: UIState<STATE>,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow: StateFlow<UIState<STATE>> = _uiStateFlow.asStateFlow()

    // TODO: A different mechanism may be used to handle effect like channel.
    // Currently using Shared flow which could cause effect lose if collector is paused.
    // These are typically viewModel events. User events can be handled within UI.
    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    val uiState: UIState<STATE> get() = uiStateFlow.value

    val contentState: STATE? get() = uiState.contentState

    abstract fun processEvent(event: EVENT)

    // TODO: Prevent setting newState. Always use copy to avoid wrong state being set when done in parallel.
    protected fun setState(newState: UIState<STATE>.() -> UIState<STATE>) {
        _uiStateFlow.update(newState)
    }

    protected fun setEffect(newEffect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    private fun state(): UIState<STATE> {
        return uiStateFlow.value
    }

    companion object{
        private const val TAG = "StatefulViewModel"
    }

}
