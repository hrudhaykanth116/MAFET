package com.hrudhaykanth116.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun getIODispatcher(): CoroutineDispatcher = Dispatchers.IO
