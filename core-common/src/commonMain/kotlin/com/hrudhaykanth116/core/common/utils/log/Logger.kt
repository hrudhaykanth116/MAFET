package com.hrudhaykanth116.core.common.utils.log

val Any.CLASS_TAG: String
    get() = this::class.simpleName ?: "Unknown"

val Any.COMPOSE_TAG: String
    get() = "Compose_tag"

// expect/actual for platform-specific logging
expect object Logger {
    fun v(tag: String, msg: String)
    fun d(tag: String, msg: String)
    fun i(tag: String, msg: String)
    fun w(tag: String, msg: String)
    fun e(tag: String, msg: String)
    fun e(tag: String, msg: String, exception: Throwable?)
}
