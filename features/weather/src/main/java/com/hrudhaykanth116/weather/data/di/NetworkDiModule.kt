package com.hrudhaykanth116.weather.data.di

import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.OpenWeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkDiModule {

    @Named("weather_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/"


    @Named("weather_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        @Named("weather_baseurl") baseUrl: String,
    ): Retrofit = retrofitBuilder
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideForeCastApiService(@Named("weather_retrofit") retrofit: Retrofit): OpenWeatherApiService =
        retrofit.create(OpenWeatherApiService::class.java)

}