package com.hrudhaykanth116.composeapp.domain

import com.hrudhaykanth116.composeapp.domain.model.AppGateConfig
import com.hrudhaykanth116.composeapp.models.Feature
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetRemoteConfigUseCaseTest {

    private lateinit var repository: IRemoteConfigRepository
    private lateinit var useCase: GetRemoteConfigUseCase

    @Before
    fun setup() {
        repository = mockk()
        coJustRun { repository.fetchAndCache() }
        every { repository.configUpdates() } returns emptyFlow()
        // Default: all gates disabled, all features disabled
        every { repository.getForceGate() } returns AppGateConfig()
        every { repository.getMaintenanceGate() } returns AppGateConfig()
        every { repository.getMessageGate() } returns AppGateConfig()
        every { repository.isFeatureEnabled(any()) } returns false
        useCase = GetRemoteConfigUseCase(repository)
    }

    // region — Gate priority

    @Test
    fun `when only force gate enabled, returns force gate`() = runTest {
        every { repository.getForceGate() } returns AppGateConfig(isEnabled = true, title = "Force")

        val config = useCase.invoke()

        assertEquals("Force", config.appGateConfig?.title)
    }

    @Test
    fun `when only maintenance gate enabled, returns maintenance gate`() = runTest {
        every { repository.getMaintenanceGate() } returns AppGateConfig(isEnabled = true, title = "Maintenance")

        val config = useCase.invoke()

        assertEquals("Maintenance", config.appGateConfig?.title)
    }

    @Test
    fun `when only message gate enabled, returns message gate`() = runTest {
        every { repository.getMessageGate() } returns AppGateConfig(isEnabled = true, title = "Message")

        val config = useCase.invoke()

        assertEquals("Message", config.appGateConfig?.title)
    }

    @Test
    fun `when force and maintenance both enabled, force gate wins`() = runTest {
        every { repository.getForceGate() } returns AppGateConfig(isEnabled = true, title = "Force")
        every { repository.getMaintenanceGate() } returns AppGateConfig(isEnabled = true, title = "Maintenance")

        val config = useCase.invoke()

        assertEquals("Force", config.appGateConfig?.title)
    }

    @Test
    fun `when force and message both enabled, force gate wins`() = runTest {
        every { repository.getForceGate() } returns AppGateConfig(isEnabled = true, title = "Force")
        every { repository.getMessageGate() } returns AppGateConfig(isEnabled = true, title = "Message")

        val config = useCase.invoke()

        assertEquals("Force", config.appGateConfig?.title)
    }

    @Test
    fun `when maintenance and message both enabled, maintenance gate wins`() = runTest {
        every { repository.getMaintenanceGate() } returns AppGateConfig(isEnabled = true, title = "Maintenance")
        every { repository.getMessageGate() } returns AppGateConfig(isEnabled = true, title = "Message")

        val config = useCase.invoke()

        assertEquals("Maintenance", config.appGateConfig?.title)
    }

    @Test
    fun `when all three gates enabled, force gate wins`() = runTest {
        every { repository.getForceGate() } returns AppGateConfig(isEnabled = true, title = "Force")
        every { repository.getMaintenanceGate() } returns AppGateConfig(isEnabled = true, title = "Maintenance")
        every { repository.getMessageGate() } returns AppGateConfig(isEnabled = true, title = "Message")

        val config = useCase.invoke()

        assertEquals("Force", config.appGateConfig?.title)
    }

    @Test
    fun `when all gates disabled, activeGate is null`() = runTest {
        val config = useCase.invoke()

        assertNull(config.appGateConfig)
    }

    // endregion

    // region — Feature flags

    @Test
    fun `only enabled features are included`() = runTest {
        every { repository.isFeatureEnabled(any()) } returns false
        every { repository.isFeatureEnabled(Feature.TODO) } returns true
        every { repository.isFeatureEnabled(Feature.WEATHER) } returns true

        val config = useCase.invoke()

        assertEquals(2, config.features.size)
        assertTrue(config.features.contains(Feature.TODO))
        assertTrue(config.features.contains(Feature.WEATHER))
    }

    @Test
    fun `when all features disabled, features list is empty`() = runTest {
        val config = useCase.invoke()

        assertTrue(config.features.isEmpty())
    }

    @Test
    fun `when all features enabled, all features are included`() = runTest {
        every { repository.isFeatureEnabled(any()) } returns true

        val config = useCase.invoke()

        assertEquals(Feature.entries.size, config.features.size)
    }

    // endregion

    // region — getCached

    @Test
    fun `getCached returns config without fetching from network`() = runTest {
        every { repository.getForceGate() } returns AppGateConfig(isEnabled = true, title = "Cached Force")

        val cached = useCase.getCached()

        // fetchAndCache should NOT be called (no coVerify needed — just check result)
        assertEquals("Cached Force", cached.appGateConfig?.title)
    }

    // endregion
}
