package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.platform.MediaPlatformActions
import org.koin.dsl.module

actual val platformMediaModule = module {
    single<MediaPlatformActions> {
        MediaPlatformActions()
    }
}
