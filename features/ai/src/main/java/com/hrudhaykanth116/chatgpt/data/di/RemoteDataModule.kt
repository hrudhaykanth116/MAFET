package com.hrudhaykanth116.chatgpt.data.di

import com.hrudhaykanth116.chatgpt.data.datasources.remote.retrofit.ChatGptQueryApiService
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

    fun provideChatGptQueryApiService(
        retrofit: Retrofit
    ): ChatGptQueryApiService {
        return retrofit.create(ChatGptQueryApiService::class.java)
    }

}