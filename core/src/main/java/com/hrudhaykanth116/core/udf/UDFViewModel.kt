package com.hrudhaykanth116.core.udf

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class UDFViewModel<STATE, EVENT, EFFECT>(
    private val initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val stateFlow: StateFlow<STATE> = _state.asStateFlow()

    @Composable
    fun collectAsState() = stateFlow.collectAsStateWithLifecycle()

    // TODO: A different mechanism may be used to handle effect like channel.
    // Currently using Shared flow which could cause effect lose if collector is paused.
    // These are typically viewModel events. User events can be handled within UI.
    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    val state: STATE get() = stateFlow.value

    abstract fun processEvent(event: EVENT)

    // TODO: Prevent setting newState. Always use copy to avoid wrong state being set when done in parallel.
    protected fun setState(newState: STATE.() -> STATE) {
        _state.update(newState)
    }

    // TODO: Find more concise way
    protected fun updateState(newState: STATE.() -> STATE) {
        setState {
            newState()
        }
    }

    fun resetState(){
        setState {
            initialState
        }
    }

    protected fun setEffect(newEffect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(newEffect)
        }
    }

    private fun state(): STATE {
        return stateFlow.value
    }

    companion object{
        private const val TAG = "StatefulViewModel"
    }

}
