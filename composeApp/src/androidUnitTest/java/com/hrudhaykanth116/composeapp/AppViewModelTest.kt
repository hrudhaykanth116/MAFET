package com.hrudhaykanth116.composeapp

import com.hrudhaykanth116.composeapp.domain.GetRemoteConfigUseCase
import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig
import com.hrudhaykanth116.composeapp.domain.model.GateAction
import com.hrudhaykanth116.composeapp.domain.model.GateButton
import com.hrudhaykanth116.composeapp.domain.model.RemoteAppConfig
import com.hrudhaykanth116.composeapp.models.AppScreenEffect
import com.hrudhaykanth116.composeapp.models.AppScreenEvent
import com.hrudhaykanth116.composeapp.models.Feature
import com.hrudhaykanth116.composeapp.testutils.MainCoroutineRule
import com.hrudhaykanth116.core.ui.NetworkMonitor
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AppViewModel
    private lateinit var getRemoteConfigUseCase: GetRemoteConfigUseCase
    private lateinit var networkMonitor: NetworkMonitor

    @Before
    fun setup() {
        getRemoteConfigUseCase = mockk()
        networkMonitor = mockk()
        every { networkMonitor.isNetworkAvailable() } returns true
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()
    }

    private fun createViewModel(): AppViewModel {
        return AppViewModel(getRemoteConfigUseCase, networkMonitor).also {
            it.initializeData()
        }
    }

    // region — Config loading

    @Test
    fun `when config loaded with no gate, activeGate is null`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = null)

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertNull(viewModel.contentStateOrDefault.activeGate)
    }

    @Test
    fun `when config loaded with enabled gate, activeGate is set`() = runTest {
        val gate = AppGateConfig(isEnabled = true, title = "Update Required")
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = gate)

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(gate, viewModel.contentStateOrDefault.activeGate)
    }

    @Test
    fun `when config loaded, features from config are set in state`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(
            features = listOf(Feature.TODO, Feature.WEATHER)
        )

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(Feature.TODO, Feature.WEATHER), viewModel.contentStateOrDefault.features)
    }

    @Test
    fun `loading state is shown while config is being fetched`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()

        // Before advancing — still loading
        val stateBeforeIdle = viewModel.uiStateFlow.value
        assertNotNull(stateBeforeIdle) // UIState.Loading
    }

    // endregion

    // region — GateButtonAction: dismiss

    @Test
    fun `dismiss action clears activeGate from state`() = runTest {
        val gate = AppGateConfig(isEnabled = true, title = "Maintenance")
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = gate)

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction(GateAction.DISMISS))

        assertNull(viewModel.contentStateOrDefault.activeGate)
    }

    @Test
    fun `dismiss action does not affect features in state`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(
            appGateConfig = AppGateConfig(isEnabled = true, title = "Gate"),
            features = listOf(Feature.TODO)
        )

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction(GateAction.DISMISS))

        assertEquals(listOf(Feature.TODO), viewModel.contentStateOrDefault.features)
    }

    // endregion

    // region — GateButtonAction: URL

    @Test
    fun `https URL action emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val url = "https://play.google.com/store/apps/details?id=com.hrudhaykanth116.mafet"
        viewModel.processEvent(AppScreenEvent.GateButtonAction(url))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        assertEquals(AppScreenEffect.OpenUrl(url), collectedEffects[0])
        job.cancel()
    }

    @Test
    fun `http URL action emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        val url = "http://example.com"
        viewModel.processEvent(AppScreenEvent.GateButtonAction(url))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        assertEquals(AppScreenEffect.OpenUrl(url), collectedEffects[0])
        job.cancel()
    }

    @Test
    fun `URL action does not change state`() = runTest {
        val gate = AppGateConfig(isEnabled = true, title = "Force")
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = gate)

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("https://play.google.com"))

        assertEquals(gate, viewModel.contentStateOrDefault.activeGate)
    }

    // endregion

    // region — GateButtonAction: unknown / edge cases

    @Test
    fun `unknown action string emits no effect and changes no state`() = runTest {
        val gate = AppGateConfig(isEnabled = true, title = "Gate")
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = gate)

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("some_unknown_key"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(collectedEffects.isEmpty())
        assertEquals(gate, viewModel.contentStateOrDefault.activeGate)
        job.cancel()
    }

    @Test
    fun `empty action string emits no effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction(""))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(collectedEffects.isEmpty())
        job.cancel()
    }

    @Test
    fun `plain text that looks like a label is not treated as URL or dismiss`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("play.google.com/store")) // no scheme
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(collectedEffects.isEmpty())
        job.cancel()
    }

    // endregion

    // region — GateButtonAction: deeplinks / URI schemes

    @Test
    fun `mailto URI emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("mailto:hrudhaykanth116@gmail.com"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        assertEquals(AppScreenEffect.OpenUrl("mailto:hrudhaykanth116@gmail.com"), collectedEffects[0])
        job.cancel()
    }

    @Test
    fun `tel URI emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("tel:+919876543210"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        assertEquals(AppScreenEffect.OpenUrl("tel:+919876543210"), collectedEffects[0])
        job.cancel()
    }

    @Test
    fun `custom deep link URI emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("mafet://feature/todo"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        assertEquals(AppScreenEffect.OpenUrl("mafet://feature/todo"), collectedEffects[0])
        job.cancel()
    }

    @Test
    fun `market URI for Play Store emits OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        viewModel.processEvent(AppScreenEvent.GateButtonAction("market://details?id=com.hrudhaykanth116.mafet"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, collectedEffects.size)
        job.cancel()
    }

    @Test
    fun `URI without scheme does not emit OpenUrl effect`() = runTest {
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig()

        viewModel = createViewModel()
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        // No scheme prefix — should be treated as unknown action
        viewModel.processEvent(AppScreenEvent.GateButtonAction("hrudhaykanth116@gmail.com"))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertTrue(collectedEffects.isEmpty())
        job.cancel()
    }

    // endregion

    // region — Gate buttons

    @Test
    fun `gate with multiple buttons renders all button actions correctly`() = runTest {
        val gate = AppGateConfig(
            isEnabled = true,
            title = "Notice",
            buttons = listOf(
                GateButton(text = "Update", action = "https://play.google.com"),
                GateButton(text = "Dismiss", action = GateAction.DISMISS),
            )
        )
        coEvery { getRemoteConfigUseCase.invoke() } returns RemoteAppConfig(appGateConfig = gate)

        viewModel = createViewModel()
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        // URL button
        val collectedEffects = mutableListOf<AppScreenEffect>()
        val job = launch { viewModel.effect.collect { collectedEffects.add(it) } }

        viewModel.processEvent(AppScreenEvent.GateButtonAction(gate.buttons[0].action))
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertEquals(1, collectedEffects.size)

        // Dismiss button
        viewModel.processEvent(AppScreenEvent.GateButtonAction(gate.buttons[1].action))
        assertNull(viewModel.contentStateOrDefault.activeGate)

        job.cancel()
    }

    // endregion
}
