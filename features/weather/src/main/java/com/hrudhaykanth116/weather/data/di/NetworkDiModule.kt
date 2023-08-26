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
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkDiModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/"

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            // .addInterceptor()
            // .addTimeOut()
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideForeCastApiService(retrofit: Retrofit): WeatherForeCastApiService =
        retrofit.create(WeatherForeCastApiService::class.java)

    @Provides
    @Singleton
    fun provideGeoCodingApiService(retrofit: Retrofit): WeatherMapGeoCodeApiService =
        retrofit.create(WeatherMapGeoCodeApiService::class.java)


}