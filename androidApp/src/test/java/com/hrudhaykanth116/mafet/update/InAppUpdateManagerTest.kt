package com.hrudhaykanth116.mafet.update

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InAppUpdateManagerTest {

    private lateinit var mockAppUpdateManager: AppUpdateManager
    private lateinit var mockTask: Task<AppUpdateInfo>
    private lateinit var mockInfo: AppUpdateInfo
    private lateinit var mockActivity: AppCompatActivity
    private lateinit var mockLauncher: ActivityResultLauncher<IntentSenderRequest>
    private lateinit var manager: InAppUpdateManager

    @Before
    fun setup() {
        mockAppUpdateManager = mockk(relaxed = true)
        mockTask = mockk()
        mockInfo = mockk()
        mockActivity = mockk(relaxed = true)
        mockLauncher = mockk(relaxed = true)

        every { mockAppUpdateManager.appUpdateInfo } returns mockTask
        every { mockTask.addOnFailureListener(any()) } returns mockTask

        manager = InAppUpdateManager(
            context = mockk(relaxed = true),
            appUpdateManager = mockAppUpdateManager
        )
    }

    // region helpers

    private fun givenSuccess() {
        every { mockTask.addOnSuccessListener(any()) } answers {
            firstArg<OnSuccessListener<AppUpdateInfo>>().onSuccess(mockInfo)
            mockTask
        }
    }

    private fun givenFailure(error: Exception = RuntimeException("network error")) {
        every { mockTask.addOnSuccessListener(any()) } returns mockTask
        every { mockTask.addOnFailureListener(any()) } answers {
            firstArg<OnFailureListener>().onFailure(error)
            mockTask
        }
    }

    private fun givenForceUpdateAvailable() {
        every { mockInfo.updateAvailability() } returns UpdateAvailability.UPDATE_AVAILABLE
        every { mockInfo.updatePriority() } returns 5
        every { mockInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) } returns true
        every { mockInfo.installStatus() } returns InstallStatus.UNKNOWN
    }

    private fun givenOptionalUpdateAvailable() {
        every { mockInfo.updateAvailability() } returns UpdateAvailability.UPDATE_AVAILABLE
        every { mockInfo.updatePriority() } returns 2
        every { mockInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) } returns false
        every { mockInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) } returns true
        every { mockInfo.installStatus() } returns InstallStatus.UNKNOWN
    }

    private fun givenNoUpdateAvailable() {
        every { mockInfo.updateAvailability() } returns UpdateAvailability.UPDATE_NOT_AVAILABLE
        every { mockInfo.updatePriority() } returns 0
        every { mockInfo.installStatus() } returns InstallStatus.UNKNOWN
    }

    private fun givenFlexibleAlreadyDownloaded() {
        every { mockInfo.updateAvailability() } returns UpdateAvailability.UPDATE_AVAILABLE
        every { mockInfo.updatePriority() } returns 2
        every { mockInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) } returns false
        every { mockInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) } returns false
        every { mockInfo.installStatus() } returns InstallStatus.DOWNLOADED
    }

    // endregion

    @Test
    fun `checkAndStartUpdate without launcher does nothing`() {
        // launcher not registered — manager should return early
        givenSuccess()
        givenForceUpdateAvailable()

        manager.checkAndStartUpdate(mockActivity)

        verify(exactly = 0) { mockAppUpdateManager.startUpdateFlowForResult(any(), any<AppCompatActivity>(), any(), any()) }
    }

    @Test
    fun `checkAndStartUpdate with force update starts IMMEDIATE flow`() {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenForceUpdateAvailable()

        manager.checkAndStartUpdate(mockActivity)

        verify {
            mockAppUpdateManager.startUpdateFlowForResult(
                mockInfo,
                mockActivity,
                match { it.appUpdateType() == AppUpdateType.IMMEDIATE },
                any()
            )
        }
        // flexible listener must NOT be registered for force update
        verify(exactly = 0) { mockAppUpdateManager.registerListener(any()) }
    }

    @Test
    fun `checkAndStartUpdate with optional update starts FLEXIBLE flow and registers listener`() {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenOptionalUpdateAvailable()

        manager.checkAndStartUpdate(mockActivity)

        verify { mockAppUpdateManager.registerListener(any()) }
        verify {
            mockAppUpdateManager.startUpdateFlowForResult(
                mockInfo,
                mockActivity,
                match { it.appUpdateType() == AppUpdateType.FLEXIBLE },
                any()
            )
        }
    }

    @Test
    fun `checkAndStartUpdate when no update available does nothing`() {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenNoUpdateAvailable()

        manager.checkAndStartUpdate(mockActivity)

        verify(exactly = 0) { mockAppUpdateManager.startUpdateFlowForResult(any(), any<AppCompatActivity>(), any(), any()) }
        verify(exactly = 0) { mockAppUpdateManager.registerListener(any()) }
    }

    @Test
    fun `checkAndStartUpdate when flexible update already downloaded emits event`() = runTest {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenFlexibleAlreadyDownloaded()

        val events = mutableListOf<InAppUpdateEvent>()
        val job = launch { manager.events.collect { events.add(it) } }
        advanceUntilIdle() // let collector subscribe before event fires

        manager.checkAndStartUpdate(mockActivity)
        advanceUntilIdle()

        assertEquals(1, events.size)
        assertEquals(InAppUpdateEvent.FlexibleUpdateReadyToInstall, events.first())
        job.cancel()
    }

    @Test
    fun `checkAndStartUpdate on failure does not emit event`() = runTest {
        manager.registerLauncher(mockLauncher)
        givenFailure()

        val events = mutableListOf<InAppUpdateEvent>()
        val job = launch { manager.events.collect { events.add(it) } }

        manager.checkAndStartUpdate(mockActivity)
        advanceUntilIdle()

        assertEquals(0, events.size)
        job.cancel()
    }

    @Test
    fun `flexible install listener emits event and unregisters on download complete`() = runTest {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenOptionalUpdateAvailable()

        val listenerSlot = slot<InstallStateUpdatedListener>()
        every { mockAppUpdateManager.registerListener(capture(listenerSlot)) } just runs

        manager.checkAndStartUpdate(mockActivity)

        val events = mutableListOf<InAppUpdateEvent>()
        val job = launch { manager.events.collect { events.add(it) } }
        advanceUntilIdle() // let collector subscribe before event fires

        val mockState = mockk<InstallState>()
        every { mockState.installStatus() } returns InstallStatus.DOWNLOADED
        listenerSlot.captured.onStateUpdate(mockState)
        advanceUntilIdle()

        assertEquals(1, events.size)
        assertEquals(InAppUpdateEvent.FlexibleUpdateReadyToInstall, events.first())
        verify { mockAppUpdateManager.unregisterListener(listenerSlot.captured) }
        job.cancel()
    }

    @Test
    fun `flexible install listener ignores non-downloaded states`() = runTest {
        manager.registerLauncher(mockLauncher)
        givenSuccess()
        givenOptionalUpdateAvailable()

        val listenerSlot = slot<InstallStateUpdatedListener>()
        every { mockAppUpdateManager.registerListener(capture(listenerSlot)) } just runs

        manager.checkAndStartUpdate(mockActivity)

        val events = mutableListOf<InAppUpdateEvent>()
        val job = launch { manager.events.collect { events.add(it) } }

        val mockState = mockk<InstallState>()
        every { mockState.installStatus() } returns InstallStatus.DOWNLOADING
        listenerSlot.captured.onStateUpdate(mockState)
        advanceUntilIdle()

        assertEquals(0, events.size)
        job.cancel()
    }

    @Test
    fun `completeFlexibleUpdate calls completeUpdate on manager`() {
        manager.completeFlexibleUpdate()
        verify { mockAppUpdateManager.completeUpdate() }
    }

    @Test
    fun `unregisterListeners unregisters flexible listener from manager`() {
        manager.unregisterListeners()
        verify { mockAppUpdateManager.unregisterListener(any()) }
    }
}
