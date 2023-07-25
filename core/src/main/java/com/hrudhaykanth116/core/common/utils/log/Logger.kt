package com.hrudhaykanth116.core.common.utils.log

import android.util.Log
import java.lang.Exception

val Any.CLASS_TAG: String
    get() = javaClass.simpleName

val Any.COMPOSE_TAG: String
    get() = "Compose_tag"

object Logger {

    private const val TAG = "Mafet"
    private const val LOG_ENABLE = true

    private fun printMsg(logType: Int, tag: String, message: String) {
        val maxLogSize = 2000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end

            when (logType) {
                Log.ASSERT -> {
                    Log.v(tag, "=> ${message.substring(start, end)}")
                }
                Log.DEBUG -> {
                    Log.d(tag, "=> ${message.substring(start, end)}")
                }
                Log.ERROR -> {
                    Log.e(tag, "=> ${message.substring(start, end)}")
                }
                Log.INFO -> {
                    Log.i(tag, "=> ${message.substring(start, end)}")
                }
                Log.VERBOSE -> {
                    Log.v(tag, "=> ${message.substring(start, end)}")
                }
                Log.WARN -> {
                    Log.w(tag, "=> ${message.substring(start, end)}")
                }
            }
        }
    }

    fun v(tag: String, msg: String) {
        if (LOG_ENABLE) {
            printMsg(Log.VERBOSE, tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (LOG_ENABLE) {
            printMsg(Log.DEBUG, tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (LOG_ENABLE) {
            printMsg(Log.INFO, tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (LOG_ENABLE) {
            printMsg(Log.WARN, tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (LOG_ENABLE) {
            printMsg(Log.ERROR, tag, msg)
        }
    }

    fun e(tag: String, msg: String, exception: Exception? = null) {
        if (LOG_ENABLE) {
            Log.e(tag, "$msg: ", exception)
        }
    }

}