package com.hrudhaykanth116.mafet.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.hrudhaykanth116.composeapp.App
import com.hrudhaykanth116.mafet.main.MainUiState
import com.hrudhaykanth116.mafet.main.MainViewModel
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.mafet.update.InAppUpdateEvent
import com.hrudhaykanth116.mafet.update.InAppUpdateManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val inAppUpdateManager: InAppUpdateManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        var uiState: MainUiState by mutableStateOf(MainUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            Logger.d(TAG, "onCreate: setKeepOnScreenCondition")
            // This condition is checked every frame.
            uiState is MainUiState.Loading
        }

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        inAppUpdateManager.events.collect { event ->
                            when (event) {
                                InAppUpdateEvent.FlexibleUpdateReadyToInstall -> {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Update downloaded.",
                                        actionLabel = "Restart",
                                        duration = SnackbarDuration.Indefinite
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        inAppUpdateManager.completeFlexibleUpdate()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val navigationEventDispatcher = remember { NavigationEventDispatcher() }

            Box(modifier = Modifier.fillMaxSize()) {
                CompositionLocalProvider(
                    LocalNavigationEventDispatcherOwner provides object : androidx.navigationevent.NavigationEventDispatcherOwner {
                        override val navigationEventDispatcher = navigationEventDispatcher
                    }
                ) {
                    App()
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inAppUpdateManager.checkAndStartUpdate(this)
    }

    override fun onStop() {
        super.onStop()
        inAppUpdateManager.unregisterListeners()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
