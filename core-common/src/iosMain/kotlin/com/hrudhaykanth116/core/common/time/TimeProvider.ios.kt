package com.hrudhaykanth116.core.common.time

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class SystemTimeProvider actual constructor() : TimeProvider {
    actual override fun currentTimeMillis(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}
