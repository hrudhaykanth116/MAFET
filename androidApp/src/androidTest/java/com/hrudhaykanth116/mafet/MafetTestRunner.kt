package com.hrudhaykanth116.mafet

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/**
 * Custom test runner so the instrumentation uses TestMafetApplication
 * instead of the real MafetApplication when tests run on device.
 * Configured via testInstrumentationRunner in androidApp/build.gradle.kts.
 */
class MafetTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, ctx: Context?): Application =
        super.newApplication(cl, TestMafetApplication::class.java.name, ctx)
}
