package com.hrudhaykanth116.mafet.di

import com.hrudhaykanth116.composeapp.data.RemoteConfigDataSource
import com.hrudhaykanth116.mafet.CrashHandler
import com.hrudhaykanth116.mafet.ads.AdsInitializer
import com.hrudhaykanth116.mafet.update.InAppUpdateManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.dsl.module

// Gate JSON with is_enabled=false so no force-update/maintenance dialog blocks the UI during tests.
private const val DISABLED_GATE_JSON =
    """{"is_enabled":false,"type":"force","title":"","message":"","buttons":[]}"""

/**
 * Overrides production dependencies that require network or device services.
 * These run on a real emulator/device, so anything that calls Firebase or Play
 * must be stubbed out or the test will fail / time out.
 */
val testAppModule = module {

    // All feature flags returned as true so every tab is visible in the bottom nav.
    // Gate strings use the disabled JSON so no blocking dialogs appear.
    single<RemoteConfigDataSource> {
        mockk(relaxed = true) {
            every { getBoolean("feature_todo") } returns true
            every { getBoolean("feature_journal") } returns true
            every { getBoolean("feature_ai") } returns true
            every { getBoolean("feature_weather") } returns true
            every { getBoolean("feature_watchlist") } returns true
            every { getBoolean("feature_media") } returns true
            every { getString("app_gate_force") } returns DISABLED_GATE_JSON
            every { getString("app_gate_maintenance") } returns DISABLED_GATE_JSON
            every { getString("app_gate_message") } returns DISABLED_GATE_JSON
            every { configUpdates() } returns emptyFlow()
            coEvery { fetchAndActivate() } returns true
        }
    }

    single<CrashHandler> { mockk(relaxed = true) }
    single<AdsInitializer> { mockk(relaxed = true) }

    // events must be a real MutableSharedFlow, not a MockK default.
    // MockK's relaxed stub for a suspend collect() breaks coroutine state machines.
    single<InAppUpdateManager> {
        mockk(relaxed = true) {
            every { events } returns MutableSharedFlow()
        }
    }
}
