package com.hrudhaykanth116.todo.di

import com.hrudhaykanth116.todo.data.remote.retrofit.TodoTasksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteDataModule {

    @Provides
    @Singleton
    @Named("todo_base_url")
    fun provideBaseUrl(): String = "https://dummyjson.com/"

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Named("todo_base_url") baseUrl: String,
    ): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoTasksApiService(
        retrofit: Retrofit
    ): TodoTasksApiService =
        retrofit.create(TodoTasksApiService::class.java)

}