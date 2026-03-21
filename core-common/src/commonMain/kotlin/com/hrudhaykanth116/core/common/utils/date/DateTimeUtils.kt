package com.hrudhaykanth116.core.common.utils.date

import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DAY_SHORT
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR

// expect/actual for platform-specific date formatting
expect class DateTimeUtils() {
    fun getFormattedDateTime(
        timeMillis: Long?,
        pattern: String = COMPLETE_DATE_TIME_FORMAT,
    ): String?

    fun getMillisFromDateTime(
        dateTime: String?,
        pattern: String = COMPLETE_DATE_TIME_FORMAT,
    ): Long?

    companion object {
        val COMPLETE_DATE_TIME_FORMAT: String
        val HOURS_MIN_FORMAT: String
        val DAY_DATE_FORMAT: String
    }
}
