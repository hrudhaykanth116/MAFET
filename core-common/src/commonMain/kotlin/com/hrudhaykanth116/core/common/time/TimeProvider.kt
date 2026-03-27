package com.hrudhaykanth116.core.common.time

interface TimeProvider {
    fun currentTimeMillis(): Long
}

expect class SystemTimeProvider() : TimeProvider {
    override fun currentTimeMillis(): Long
}
