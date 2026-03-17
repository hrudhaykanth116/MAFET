package com.hrudhaykanth116.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    single(named("DefaultDispatcher")) { Dispatchers.Default as CoroutineDispatcher }
    single(named("IoDispatcher")) { Dispatchers.IO as CoroutineDispatcher }
    single(named("MainDispatcher")) { Dispatchers.Main as CoroutineDispatcher }
}