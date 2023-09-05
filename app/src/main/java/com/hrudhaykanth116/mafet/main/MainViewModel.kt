package com.hrudhaykanth116.mafet.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.auth.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(
        MainUiState.Loading
    )

    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {

            if (TRAINING_MODE) {
                _state.update {
                    MainUiState.Training
                }
            } else {
                // TODO: Use AuthRepo
                _state.update {
                    MainUiState.LoggedIn
                }
                // val loggedInUser: DataResult<String> = authRepository.getLoggedInUser()
            }
        }
    }

    fun onLoggedIn() {
        _state.update {
            MainUiState.LoggedIn
        }
    }

    companion object {
        const val TRAINING_MODE = false
    }

}