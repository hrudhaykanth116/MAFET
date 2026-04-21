package com.hrudhaykanth116.mafet

import android.app.Application
import com.hrudhaykanth116.core.data.local.datastore.initDataStore
import com.hrudhaykanth116.mafet.di.appModule
import com.hrudhaykanth116.mafet.di.testAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Replaces MafetApplication during instrumentation tests.
 * Loads the real appModule plus testAppModule which overrides Firebase/Ads/Crash
 * dependencies with mocks so tests don't need network or Play services.
 */
class TestMafetApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initDataStore(this)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@TestMafetApplication)
            allowOverride(true) // lets testAppModule replace singletons defined in appModule
            modules(appModule, testAppModule)
        }
    }
}
