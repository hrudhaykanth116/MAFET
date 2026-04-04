package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.platform.MediaPlatformActions
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformMediaModule = module {
    single<MediaPlatformActions> {
        MediaPlatformActions()
    }
    single(named("pexels_api_key")) { "" }
}
