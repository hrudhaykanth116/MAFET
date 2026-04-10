package com.hrudhaykanth116.composeapp

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.composeapp.domain.GetRemoteConfigUseCase
import com.hrudhaykanth116.composeapp.domain.model.GateAction
import com.hrudhaykanth116.composeapp.domain.model.RemoteAppConfig
import com.hrudhaykanth116.composeapp.models.AppScreenEffect
import com.hrudhaykanth116.composeapp.models.AppScreenEvent
import com.hrudhaykanth116.composeapp.models.AppScreenState
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.common.utils.url.isUrl
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import kotlinx.coroutines.launch

class AppViewModel(
    private val getRemoteConfig: GetRemoteConfigUseCase,
    private val networkMonitor: NetworkMonitor,
) : UIStateViewModel<AppScreenState, AppScreenEvent, AppScreenEffect>(
    initialState = UIState.Loading(),
    defaultState = AppScreenState(),
    networkMonitor = networkMonitor
) {
    override fun initializeData() {
        loadConfig()
        // observeRealTimeUpdates()
    }

    override fun processEvent(event: AppScreenEvent) {
        when (event) {
            is AppScreenEvent.GateButtonAction -> handleGateAction(event.action)
        }
    }

    private fun handleGateAction(action: String) {
        when {
            action == GateAction.DISMISS -> setIdleState { copy(activeGate = null) }
            action.isUrl()              -> setEffect(AppScreenEffect.OpenUrl(action))
        }
    }

    private fun loadConfig() {
        setLoadingState(null)
        viewModelScope.launch {
            val config = getRemoteConfig()
            Logger.d(TAG, "loadConfig: $config")
            setStateFromRemoteConfig(config)
        }
    }

    private fun observeRealTimeUpdates() {
        viewModelScope.launch {
            getRemoteConfig.configUpdates().collect {
                val config = getRemoteConfig.getCached()
                Logger.d(TAG, "realTimeUpdate: $config")
                setStateFromRemoteConfig(config)
            }
        }
    }

    private fun setStateFromRemoteConfig(config: RemoteAppConfig) {
        setIdleState {
            copy(
                features = config.features,
                activeGate = config.appGateConfig,
            )
        }
    }

    companion object {
        private const val TAG = "AppViewModel"
    }
}