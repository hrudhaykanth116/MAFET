package com.hrudhaykanth116.composeapp.data

import com.hrudhaykanth116.composeapp.domain.model.GateButton
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class RemoteConfigRepositoryTest {

    private lateinit var dataSource: RemoteConfigDataSource
    private lateinit var repository: RemoteConfigRepository

    @Before
    fun setup() {
        dataSource = mockk()
        repository = RemoteConfigRepository(dataSource)
    }

    // region — Gate parsing

    @Test
    fun `valid JSON parses isEnabled correctly`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns
            """{"is_enabled":true,"title":"Update","message":"Please update","buttons":[]}"""

        val gate = repository.getForceGate()

        assertTrue(gate.isEnabled)
    }

    @Test
    fun `valid JSON parses title and message`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MAINTENANCE) } returns
            """{"is_enabled":true,"title":"Maintenance","message":"Back shortly","buttons":[]}"""

        val gate = repository.getMaintenanceGate()

        assertEquals("Maintenance", gate.title)
        assertEquals("Back shortly", gate.message)
    }

    @Test
    fun `valid JSON parses buttons array`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns
            """{"is_enabled":true,"title":"T","message":"M","buttons":[{"text":"Update","action":"https://play.google.com"},{"text":"Later","action":"dismiss"}]}"""

        val gate = repository.getForceGate()

        assertEquals(2, gate.buttons.size)
        assertEquals(GateButton(text = "Update", action = "https://play.google.com"), gate.buttons[0])
        assertEquals(GateButton(text = "Later", action = "dismiss"), gate.buttons[1])
    }

    @Test
    fun `empty buttons array is parsed correctly`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MESSAGE) } returns
            """{"is_enabled":true,"title":"Notice","message":"Info","buttons":[]}"""

        val gate = repository.getMessageGate()

        assertTrue(gate.buttons.isEmpty())
    }

    // endregion

    // region — Fallback on bad data

    @Test
    fun `blank string returns default disabled gate`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MAINTENANCE) } returns ""

        val gate = repository.getMaintenanceGate()

        assertFalse(gate.isEnabled)
        assertEquals("", gate.title)
        assertTrue(gate.buttons.isEmpty())
    }

    @Test
    fun `whitespace-only string returns default disabled gate`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns "   "

        val gate = repository.getForceGate()

        assertFalse(gate.isEnabled)
    }

    @Test
    fun `malformed JSON returns default disabled gate`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MESSAGE) } returns "not_json_at_all"

        val gate = repository.getMessageGate()

        assertFalse(gate.isEnabled)
        assertEquals("", gate.title)
    }

    @Test
    fun `partially valid JSON falls back gracefully`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns """{"is_enabled": true, buttons: ["""

        val gate = repository.getForceGate()

        assertFalse(gate.isEnabled)
    }

    // endregion

    // region — JSON resilience

    @Test
    fun `unknown JSON keys are ignored due to ignoreUnknownKeys`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns
            """{"is_enabled":true,"title":"Test","unknown_field":"value","extra":123,"buttons":[]}"""

        val gate = repository.getForceGate()

        assertTrue(gate.isEnabled)
        assertEquals("Test", gate.title)
    }

    @Test
    fun `missing optional fields use defaults`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns
            """{"is_enabled":true}"""

        val gate = repository.getForceGate()

        assertTrue(gate.isEnabled)
        assertEquals("", gate.title)
        assertEquals("", gate.message)
        assertTrue(gate.buttons.isEmpty())
    }

    @Test
    fun `isEnabled false in JSON keeps gate disabled`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MAINTENANCE) } returns
            """{"is_enabled":false,"title":"Maintenance","message":"Back soon","buttons":[]}"""

        val gate = repository.getMaintenanceGate()

        assertFalse(gate.isEnabled)
    }

    // endregion

    // region — Each key maps to correct gate

    @Test
    fun `getForceGate reads from APP_GATE_FORCE key`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_FORCE) } returns
            """{"is_enabled":true,"title":"Force","message":"","buttons":[]}"""
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MAINTENANCE) } returns
            """{"is_enabled":true,"title":"Maintenance","message":"","buttons":[]}"""

        val gate = repository.getForceGate()

        assertEquals("Force", gate.title)
    }

    @Test
    fun `getMaintenanceGate reads from APP_GATE_MAINTENANCE key`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MAINTENANCE) } returns
            """{"is_enabled":true,"title":"Maintenance","message":"","buttons":[]}"""

        val gate = repository.getMaintenanceGate()

        assertEquals("Maintenance", gate.title)
    }

    @Test
    fun `getMessageGate reads from APP_GATE_MESSAGE key`() {
        every { dataSource.getString(RemoteConfigKeys.APP_GATE_MESSAGE) } returns
            """{"is_enabled":true,"title":"Message","message":"","buttons":[]}"""

        val gate = repository.getMessageGate()

        assertEquals("Message", gate.title)
    }

    // endregion
}
