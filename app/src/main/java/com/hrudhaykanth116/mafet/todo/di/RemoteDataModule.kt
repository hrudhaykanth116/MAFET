package com.hrudhaykanth116.mafet.todo.di

import com.hrudhaykanth116.mafet.todo.data.remote.retrofit.TodoTasksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    // @Provides
    // @Singleton
    // fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    //     val httpLoggingInterceptor = HttpLoggingInterceptor()
    //     httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    //     OkHttpClient.Builder()
    //         .connectTimeout(30, TimeUnit.SECONDS)
    //         .readTimeout(30, TimeUnit.SECONDS)
    //         .authenticator(TokenAuthenticator())
    //         .addInterceptor(RequestHeaderInterceptor())
    //         // .addInterceptor(httpLoggingInterceptor)
    //         .build()
    // } else OkHttpClient.Builder()
    //     .connectTimeout(30, TimeUnit.SECONDS)
    //     .readTimeout(30, TimeUnit.SECONDS)
    //     .authenticator(TokenAuthenticator())
    //     .addInterceptor(RequestHeaderInterceptor()).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        // okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("")
            .build()

    @Provides
    @Singleton
    fun provideTodoTasksApiService(retrofit: Retrofit): TodoTasksApiService =
        retrofit.create(TodoTasksApiService::class.java)

}