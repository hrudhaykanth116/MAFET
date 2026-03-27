package com.hrudhaykanth116.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

// TODO: ksp conflicts and stability issues. will use in future.
// @Qualifier
// @Retention(AnnotationRetention.BINARY)
// annotation class IoDispatcher
//
// @Qualifier
// @Retention(AnnotationRetention.BINARY)
// annotation class MainDispatcher
//
// @Qualifier
// @Retention(AnnotationRetention.BINARY)
// annotation class DefaultDispatcher
//
// @Single
// @IoDispatcher
// fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
//
// @Single
// @MainDispatcher
// fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
//
// @Single
// @DefaultDispatcher
// fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

enum class DispatchersEnum {
    IoDispatcher,
    MainDispatcher,
    DefaultDispatcher
}

expect fun getIODispatcher(): CoroutineDispatcher

val dispatchersModule = module {
    single(named(DispatchersEnum.IoDispatcher)) {
        getIODispatcher()
    }
    single(named(DispatchersEnum.MainDispatcher)) {
        Dispatchers.Main as CoroutineDispatcher
    }
    single(named(DispatchersEnum.DefaultDispatcher)) {
        Dispatchers.Default as CoroutineDispatcher
    }
}

// interface DispatcherProvider {
//     val io: CoroutineDispatcher
//     val main: CoroutineDispatcher
//     val default: CoroutineDispatcher
// }
//
// class DefaultDispatcherProvider : DispatcherProvider {
//     override val io = Dispatchers.IO
//     override val main = Dispatchers.Main
//     override val default = Dispatchers.Default
// }
//
// val dispatchersModule = module {
//     single<DispatcherProvider> { DefaultDispatcherProvider() }
// }