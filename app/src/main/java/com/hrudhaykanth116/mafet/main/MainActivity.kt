package com.hrudhaykanth116.mafet.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hrudhaykanth116.core.common.utils.Logger
import com.hrudhaykanth116.mafet.auth.ui.navigation.AuthNavigation
import com.hrudhaykanth116.mafet.home.HomeNavigation
import com.hrudhaykanth116.mafet.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var uiState: MainUiState by mutableStateOf(MainUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .onEach {
                        Logger.d(TAG, "onCreate: newState: ${it}")
                        uiState = it
                    }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            Logger.d(TAG, "onCreate: setKeepOnScreenCondition")
            uiState is MainUiState.Loading
        }

        setContent {
            MainContentUI(uiState)
        }
    }

    @Composable
    private fun MainContentUI(uiState: MainUiState) {
        AppTheme {
            Logger.d(TAG, "onCreate: setContent()")
            when (uiState) {
                MainUiState.Loading -> {
                    Text(text = "This view shouldnt be visible due to splash screen.")
                }
                is MainUiState.LoggedIn -> {
                    HomeNavigation(uiState.userName)
                }
                MainUiState.LoggedOut -> {
                    AuthNavigation()
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
