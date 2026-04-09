package com.hrudhaykanth116.composeapp

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.composeapp.models.AppScreenEffect
import com.hrudhaykanth116.composeapp.models.AppScreenEvent
import com.hrudhaykanth116.composeapp.models.AppScreenState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.viewmodels.StatefulViewModel
import kotlinx.coroutines.launch

class AppViewModel(
    private val remoteConfigManager: RemoteConfigManager,
) : StatefulViewModel<AppScreenState, AppScreenEffect, AppScreenEvent>(AppScreenState()) {

    init {
        loadConfig()
        observeRealTimeUpdates()
    }

    override fun processEvent(event: AppScreenEvent) {
        when (event) {
            AppScreenEvent.DismissDialog -> dismissDialog()
            is AppScreenEvent.DialogButtonClicked -> handleDialogAction(event.action)
        }
    }

    private fun loadConfig() {
        viewModelScope.launch {
            val config = remoteConfigManager.fetchConfig()
            Logger.d(TAG, "loadConfig: $config")
            val gate = config.appGate

            val dialogConfig = config.appEntryDialogRemoteConfig.takeIf { it.isEnabled && it.title.isNotBlank() }

            setState {
                copy(
                    features = config.features.filter { it.enabled },
                    blockingConfig = gate,
                    dialogConfig = dialogConfig,
                )
            }
        }
    }

    private fun observeRealTimeUpdates() {
        viewModelScope.launch {
            remoteConfigManager.configUpdates().collect {
                loadConfig()
            }
        }
    }

    private fun dismissDialog() {
        setState { copy(dialogConfig = null) }
    }

    private fun handleDialogAction(action: String) {
        setEffect(AppScreenEffect.HandleDialogAction(action))
        dismissDialog()
    }

    companion object {
        private const val TAG = "AppViewModel"
    }
}
