package com.hrudhaykanth116.core.common.di

import com.hrudhaykanth116.core.common.time.SystemTimeProvider
import com.hrudhaykanth116.core.common.time.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimeModule {

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider = SystemTimeProvider()
} 