package com.hrudhaykanth116.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * State: A type that describes the data your feature needs to perform its logic and render its UI.
 *      The state is persistence during feature lifetime.
 * Effect: Similar to state but it is not persistence such as navigation, show toast, show snackbar.
 * Action: A type that represents all of the actions that cause the state of the application to
 *      change such as user actions, notifications, event sources and more.
 */
abstract class StatefulViewModel<STATE, EFFECT, EVENT>(
    initialState: STATE,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val stateFlow: StateFlow<STATE> = _state.asStateFlow()

    // hrudhay_check_list: A different mechanism may be used to handle effect like channel.
    // Currently using Shared flow which could cause effect lose if collector is paused.
    // These are typically viewModel events. User events can be handled within UI.
    private val _effect: MutableSharedFlow<EFFECT> = MutableSharedFlow()
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    val state: STATE get() = stateFlow.value

    abstract fun processEvent(event: EVENT)

    // hrudhay_check_list: Prevent setting newState. Always use copy to avoid wrong state being set when done in parallel.
    protected fun setState(newState: STATE.() -> STATE) {
        _state.update(newState)
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
