package com.hrudhaykanth116.mafet

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import com.hrudhaykanth116.mafet.ads.AdsInitializer
import com.hrudhaykanth116.mafet.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MafetApplication : Application(), Application.ActivityLifecycleCallbacks {

    private val crashHandler: CrashHandler by inject()
    private val adsInitializer: AdsInitializer by inject()

    var currentActivity: Activity? = null
        private set

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MafetApplication)
            modules(appModule)
        }

        registerActivityLifecycleCallbacks(this)

        //Kot-pref initialization
        Kotpref.init(this)
        Kotpref.gson = Gson()

        crashHandler.init(this)
        launchInCoroutine { adsInitializer.initialize(this) }

    }

    private fun launchInCoroutine(suspendFunction: suspend () -> Unit) {
        coroutineScope.launch {
            suspendFunction()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

}