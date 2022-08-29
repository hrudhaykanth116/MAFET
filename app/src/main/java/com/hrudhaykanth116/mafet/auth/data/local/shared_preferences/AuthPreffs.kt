package com.hrudhaykanth116.mafet.auth.data.local.shared_preferences

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref

object AuthPreffs: KotprefModel(){

    var isLoggedIn by booleanPref(false)

    // TODO: Just an example of storing list in preffs.
    var exampleList by gsonNullablePref<MutableList<Boolean>>()

}