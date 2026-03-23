package com.hrudhaykanth116.mafet.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // To force edge to edge in older versions than 15
        enableEdgeToEdge()

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

        // WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navigationEventDispatcher = remember { NavigationEventDispatcher() }

            CompositionLocalProvider(
                LocalNavigationEventDispatcherOwner provides object : androidx.navigationevent.NavigationEventDispatcherOwner {
                    override val navigationEventDispatcher = navigationEventDispatcher
                }
            ) {
                MainActivityScreen(
                    uiState = uiState,
                    onLoggedIn = {
                        viewModel.onLoggedIn()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
