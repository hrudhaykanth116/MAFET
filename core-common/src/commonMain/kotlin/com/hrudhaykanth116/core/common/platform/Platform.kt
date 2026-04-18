package com.hrudhaykanth116.core.common.platform

enum class Platform {
    ANDROID,
    IOS,
    DESKTOP,
}

expect val currentPlatform: Platform

val isAndroid: Boolean get() = currentPlatform == Platform.ANDROID
val isIos: Boolean get() = currentPlatform == Platform.IOS
val isDesktop: Boolean get() = currentPlatform == Platform.DESKTOP
