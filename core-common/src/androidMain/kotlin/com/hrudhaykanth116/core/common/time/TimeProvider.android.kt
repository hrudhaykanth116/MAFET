package com.hrudhaykanth116.core.common.time

actual class SystemTimeProvider actual constructor() : TimeProvider {
    actual override fun currentTimeMillis(): Long = System.currentTimeMillis()
}
