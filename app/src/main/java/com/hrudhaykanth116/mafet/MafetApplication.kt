package com.hrudhaykanth116.mafet

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MafetApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        //Kot-pref initialization
        Kotpref.init(this)
        Kotpref.gson = Gson()

    }

}