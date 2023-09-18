package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.TvApisService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkDiModule {

    // TODO: Reuse dependencies from other modules.

    @Named("tv_moshi")
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Named("tv_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Named("tv_okhttp")
    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            // .addInterceptor()
            // .addTimeOut()
            .build()


    @Named("tv_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("tv_okhttp") okHttpClient: OkHttpClient,
        @Named("tv_baseurl") BASE_URL: String,
        @Named("tv_moshi") moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideRetroApis(@Named("tv_retrofit") retrofit: Retrofit): RetroApis =
        retrofit.create(RetroApis::class.java)

    @Provides
    @Singleton
    fun provideTvApisService(@Named("tv_retrofit") retrofit: Retrofit): TvApisService =
        retrofit.create(TvApisService::class.java)

}