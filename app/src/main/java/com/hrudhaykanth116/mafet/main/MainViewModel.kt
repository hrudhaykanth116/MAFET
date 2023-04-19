package com.hrudhaykanth116.mafet.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.mafet.auth.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: IAuthRepository
): ViewModel() {

    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(
        MainUiState.Loading
    )

    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _state.update {
                MainUiState.LoggedIn(
                    "Hrudhay"
                )
            }
            // val loggedInUser: DataResult<String> = authRepository.getLoggedInUser()
        }
    }

}