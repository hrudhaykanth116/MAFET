package com.hrudhaykanth116.core.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    private val _effect: MutableStateFlow<EFFECT?> = MutableStateFlow(null)

    val stateFlow: StateFlow<STATE> = _state.asStateFlow()

    val effect: StateFlow<EFFECT?> = _effect.asStateFlow()

    abstract fun processEvent(event: EVENT)

    protected fun setState(newState: STATE.() -> STATE) {
        _state.update(newState)
        Log.d(TAG, "setState: _state: ${_state}")
    }

    protected fun setEffect(newEffect: EFFECT) {
        _effect.update { newEffect }
    }

    fun resetEffect() {
        _effect.update { null }
    }

    private fun stateValue(): STATE {
        return stateFlow.value
    }

    companion object{
        private const val TAG = "StatefulViewModel"
    }

}
