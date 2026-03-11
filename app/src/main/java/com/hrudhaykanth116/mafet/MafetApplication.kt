package com.hrudhaykanth116.mafet

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import com.hrudhaykanth116.core.ads.AdsInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MafetApplication : Application(), Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var crashHandler: CrashHandler

    @Inject
    lateinit var adsInitializer: AdsInitializer

    var currentActivity: Activity? = null
        private set

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

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