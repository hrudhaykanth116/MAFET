package com.hrudhaykanth116.core.common.utils.log

import co.touchlab.kermit.Logger as KermitLogger

/**
 * Default logging implementation using Kermit library.
 * Kermit is a Kotlin Multiplatform logging library that works on all platforms.
 */
class KermitLogImplementation : LogImplementation {

    private val kermit = KermitLogger

    override fun log(
        level: LogLevel,
        tag: String,
        msg: String,
        throwable: Throwable?
    ) {
        when (level) {
            LogLevel.VERBOSE -> kermit.withTag(tag).v { msg }
            LogLevel.DEBUG -> kermit.withTag(tag).d { msg }
            LogLevel.INFO -> kermit.withTag(tag).i { msg }
            LogLevel.WARN -> kermit.withTag(tag).w { msg }
            LogLevel.ERROR -> {
                if (throwable != null) {
                    kermit.withTag(tag).e(throwable) { msg }
                } else {
                    kermit.withTag(tag).e { msg }
                }
            }
        }
    }
}
