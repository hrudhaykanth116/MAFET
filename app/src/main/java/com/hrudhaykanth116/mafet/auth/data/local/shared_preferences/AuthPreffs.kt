package com.hrudhaykanth116.mafet.auth.data.local.shared_preferences

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.gsonpref.gsonNullablePref
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthPreffs @Inject constructor(): KotprefModel(){

    var isLoggedIn by booleanPref(false)

    // TODO: Just an example of storing list in preffs.
    var gsonPreffs by gsonNullablePref<MutableList<Boolean>>()

}