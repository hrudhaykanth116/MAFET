package com.hrudhaykanth116.tv.di

import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.RetroApis
import com.hrudhaykanth116.tv.data.datasources.remote.retrofit.TvApisService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EntertainmentNetworkDiModule {


    @Named("tv_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3/"

    @Named("tv_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Named("tv_baseurl") baseUrl: String,
    ): Retrofit = retrofitBuilder
        .baseUrl(baseUrl)
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