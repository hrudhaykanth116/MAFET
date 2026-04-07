package com.hrudhaykanth116.mafet.update

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.hrudhaykanth116.core.common.utils.log.Logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed interface InAppUpdateEvent {
    object FlexibleUpdateReadyToInstall : InAppUpdateEvent
}

class InAppUpdateManager(
    context: Context,
    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context),
) {

    private val _events = MutableSharedFlow<InAppUpdateEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<InAppUpdateEvent> = _events.asSharedFlow()

    private var launcher: ActivityResultLauncher<IntentSenderRequest>? = null

    private val flexibleInstallListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Logger.d(TAG, "Flexible update downloaded, ready to install")
            _events.tryEmit(InAppUpdateEvent.FlexibleUpdateReadyToInstall)
            unregisterListeners()
        }
    }

    fun registerLauncher(launcher: ActivityResultLauncher<IntentSenderRequest>) {
        this.launcher = launcher
    }

    fun checkAndStartUpdate(activity: AppCompatActivity) {
        val launcher = this.launcher ?: return

        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                when {
                    info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && info.updatePriority() >= FORCE_UPDATE_PRIORITY
                    && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                        Logger.d(TAG, "Force update available (priority=${info.updatePriority()})")
                        appUpdateManager.startUpdateFlowForResult(
                            info,
                            activity,
                            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                            UPDATE_REQUEST_CODE
                        )
                    }

                    info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                        Logger.d(TAG, "Optional update available")
                        appUpdateManager.registerListener(flexibleInstallListener)
                        appUpdateManager.startUpdateFlowForResult(
                            info,
                            activity,
                            AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
                            UPDATE_REQUEST_CODE
                        )
                    }

                    info.installStatus() == InstallStatus.DOWNLOADED -> {
                        // Returned to app after a flexible download that already completed
                        _events.tryEmit(InAppUpdateEvent.FlexibleUpdateReadyToInstall)
                    }
                }
            }
            .addOnFailureListener { e ->
                Logger.e(TAG, "Failed to check for update", e)
            }
    }

    fun completeFlexibleUpdate() {
        appUpdateManager.completeUpdate()
    }

    fun unregisterListeners() {
        appUpdateManager.unregisterListener(flexibleInstallListener)
    }

    companion object {
        private const val TAG = "InAppUpdateManager"
        private const val UPDATE_REQUEST_CODE = 500
        private const val FORCE_UPDATE_PRIORITY = 4
    }
}
