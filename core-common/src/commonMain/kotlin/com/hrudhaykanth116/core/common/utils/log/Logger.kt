package com.hrudhaykanth116.core.common.utils.log

/**
 * Logger facade for the entire application.
 *
 * This provides a consistent logging API across all platforms while allowing
 * the underlying implementation to be swapped easily.
 *
 * Usage:
 * ```
 * Logger.d("MyTag", "Debug message")
 * Logger.e("MyTag", "Error message", exception)
 * ```
 *
 * Extension properties for convenience:
 * ```
 * val Any.CLASS_TAG: String
 * val Any.COMPOSE_TAG: String
 *
 * Logger.d(CLASS_TAG, "Message from class ${this::class.simpleName}")
 * ```
 */
object Logger {

    /**
     * Current logging implementation.
     * Default: KermitLogImplementation
     * Can be swapped with: setImplementation()
     */
    private var implementation: LogImplementation = KermitLogImplementation()

    /**
     * Set a custom logging implementation.
     * Useful for:
     * - Swapping to different libraries (Napier, custom, etc.)
     * - Adding file logging
     * - Adding remote logging (Crashlytics, etc.)
     * - Disabling logs in production
     *
     * @param impl Custom LogImplementation
     */
    fun setImplementation(impl: LogImplementation) {
        implementation = impl
    }

    /**
     * Log a VERBOSE message
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     */
    fun v(tag: String, msg: String) {
        implementation.log(LogLevel.VERBOSE, tag, msg)
    }

    /**
     * Log a DEBUG message
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     */
    fun d(tag: String, msg: String) {
        implementation.log(LogLevel.DEBUG, tag, msg)
    }

    /**
     * Log an INFO message
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     */
    fun i(tag: String, msg: String) {
        implementation.log(LogLevel.INFO, tag, msg)
    }

    /**
     * Log a WARN message
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     */
    fun w(tag: String, msg: String) {
        implementation.log(LogLevel.WARN, tag, msg)
    }

    /**
     * Log an ERROR message
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     */
    fun e(tag: String, msg: String) {
        implementation.log(LogLevel.ERROR, tag, msg)
    }

    /**
     * Log an ERROR message with exception
     * @param tag Tag for categorizing logs
     * @param msg Message to log
     * @param exception Exception/Throwable to log
     */
    fun e(tag: String, msg: String, exception: Throwable?) {
        implementation.log(LogLevel.ERROR, tag, msg, exception)
    }
}