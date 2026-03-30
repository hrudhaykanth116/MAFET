package com.hrudhaykanth116.core.common.utils.date

import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DAY_SHORT
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970

actual class DateTimeUtils {

    actual fun getFormattedDateTime(
        timeMillis: Long?,
        pattern: String,
    ): String? {
        timeMillis ?: return null

        val date = NSDate.dateWithTimeIntervalSince1970(timeMillis / 1000.0)
        val formatter = NSDateFormatter().apply {
            dateFormat = pattern
            locale = NSLocale.currentLocale
        }
        return formatter.stringFromDate(date)
    }

    actual fun getMillisFromDateTime(
        dateTime: String?,
        pattern: String,
    ): Long? {
        if (dateTime.isNullOrBlank()) return null

        val formatter = NSDateFormatter().apply {
            dateFormat = pattern
            locale = NSLocale.currentLocale
        }
        return try {
            val date = formatter.dateFromString(dateTime)
            date?.let { (it.timeIntervalSince1970 * 1000).toLong() }
        } catch (exception: Exception) {
            println("DateTimeUtils - getMillisFromDateTime error: $exception")
            null
        }
    }

    actual companion object {
        actual val COMPLETE_DATE_TIME_FORMAT =
            "$DATE $MONTH_STRING_FULL $YEAR $HOUR_12:$MINUTES $AM_PM"

        actual val HOURS_MIN_FORMAT = "HH:mm"
        actual val DAY_DATE_FORMAT = "$DAY_SHORT $DATE"
    }
}
