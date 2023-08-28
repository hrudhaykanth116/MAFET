package com.hrudhaykanth116.weather.data.di

import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.WeatherForeCastApiService
import com.hrudhaykanth116.weather.data.datasources.remote.retrofit.WeatherMapGeoCodeApiService
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

    @Named("weather_moshi")
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Named("weather_baseurl")
    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/"

    @Named("weather_okhttp")
    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            // .addInterceptor()
            // .addTimeOut()
            .build()


    @Named("weather_retrofit")
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("weather_okhttp") okHttpClient: OkHttpClient,
        @Named("weather_baseurl") BASE_URL: String,
        @Named("weather_moshi") moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideForeCastApiService(@Named("weather_retrofit") retrofit: Retrofit): WeatherForeCastApiService =
        retrofit.create(WeatherForeCastApiService::class.java)

    @Provides
    @Singleton
    fun provideGeoCodingApiService(@Named("weather_retrofit") retrofit: Retrofit): WeatherMapGeoCodeApiService =
        retrofit.create(WeatherMapGeoCodeApiService::class.java)


}