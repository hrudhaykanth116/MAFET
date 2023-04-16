package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.todo.data.remote.retrofit.TodoTasksApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder
            .baseUrl("https://dummyjson.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoTasksApiService(
        retrofit: Retrofit
    ): TodoTasksApiService =
        retrofit.create(TodoTasksApiService::class.java)

}