package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.BuildConfig
import com.hrudhaykanth116.media.platform.MediaPlatformActions
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformMediaModule = module {
    single<MediaPlatformActions> {
        MediaPlatformActions(androidContext())
    }
    single(named("pexels_api_key")) { BuildConfig.PEXELS_API_KEY }
}
