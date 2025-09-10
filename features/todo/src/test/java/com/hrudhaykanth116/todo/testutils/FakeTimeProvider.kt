package com.hrudhaykanth116.todo.testutils

import com.hrudhaykanth116.core.common.time.TimeProvider

class FakeTimeProvider(private val now: Long = 123L) : TimeProvider {
    override fun currentTimeMillis(): Long = now
}
