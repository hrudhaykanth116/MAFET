package com.hrudhaykanth116.ai.data.di

import com.hrudhaykanth116.ai.data.datasources.remote.retrofit.ChatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder
            .baseUrl("https://dummyjson.com/")
            .build()
    }

    fun provideChatApiService(
        retrofit: Retrofit
    ): ChatApiService {
        return retrofit.create(ChatApiService::class.java)
    }

}