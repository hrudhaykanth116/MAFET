package com.hrudhaykanth116.media.di

import com.hrudhaykanth116.media.BuildConfig
import com.hrudhaykanth116.media.data.network.PexelsApisService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaNetworkDiModule {

    @Provides
    @Named("pexels_api_key")
    fun providePexelsApiKey(): String = BuildConfig.PEXELS_API_KEY

    @Named("media_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.pexels.com/"


    @Named("media_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Named("media_baseurl") baseUrl: String,
    ): Retrofit = retrofitBuilder
        .baseUrl(baseUrl)
        .build()


    @Provides
    @Singleton
    fun provideTvApisService(@Named("media_retrofit") retrofit: Retrofit): PexelsApisService =
        retrofit.create(PexelsApisService::class.java)

}