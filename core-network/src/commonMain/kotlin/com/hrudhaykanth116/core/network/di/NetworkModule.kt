package com.hrudhaykanth116.core.network.di

import com.hrudhaykanth116.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * Koin module for common network dependencies (Ktor)
 */
val networkModule = module {
    single<HttpClient> {
        HttpClientFactory.create(enableLogging = true)
    }
}
