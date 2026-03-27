package com.hrudhaykanth116.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual fun getIODispatcher(): CoroutineDispatcher = Dispatchers.IO
