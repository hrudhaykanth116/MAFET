package com.hrudhaykanth116.core.di

import com.hrudhaykanth116.core.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        return if (BuildConfig.DEBUG) {
            // val httpLoggingInterceptor = HttpLoggingInterceptor()
            // httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder
                // .authenticator(TokenAuthenticator())
                // .addInterceptor(RequestHeaderInterceptor())
                // .addInterceptor(httpLoggingInterceptor)
                .build()
        } else builder
            // .authenticator(TokenAuthenticator())
            // .addInterceptor(RequestHeaderInterceptor()).build()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        // baseUrl: String,
        moshi: Moshi,
    ): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            // .baseUrl(baseUrl)
            .client(okHttpClient)

}