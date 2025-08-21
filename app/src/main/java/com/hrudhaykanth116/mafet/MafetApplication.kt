package com.hrudhaykanth116.mafet

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MafetApplication: Application(), Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var crashHandler: CrashHandler

    var currentActivity: Activity? = null
        private set

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)

        //Kot-pref initialization
        Kotpref.init(this)
        Kotpref.gson = Gson()

        crashHandler.init(this)

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