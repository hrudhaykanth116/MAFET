package com.hrudhaykanth116.core.common.utils.log

/**
 * Interface for logging implementations.
 * Allows swapping between different logging libraries (Kermit, Napier, custom, etc.)
 * without changing Logger API usage throughout the codebase.
 */
interface LogImplementation {

    /**
     * Log a message with given level, tag, message, and optional throwable.
     *
     * @param level The log level (VERBOSE, DEBUG, INFO, WARN, ERROR)
     * @param tag Tag for categorizing logs
     * @param msg The log message
     * @param throwable Optional exception/throwable to log
     */
    fun log(
        level: LogLevel,
        tag: String,
        msg: String,
        throwable: Throwable? = null
    )
}
