package com.hrudhaykanth116.core.common.utils.date

import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DAY_SHORT
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual class DateTimeUtils {

    actual fun getFormattedDateTime(
        timeMillis: Long?,
        pattern: String,
    ): String? {

        timeMillis ?: return null

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }

    actual fun getMillisFromDateTime(
        dateTime: String?,
        pattern: String,
    ): Long? {

        if (dateTime.isNullOrBlank()) return null

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            sdf.parse(dateTime)?.time
        } catch (exception: Exception) {
            System.err.println("DateTimeUtils - getMillisFromDateTime error: $exception")
            null
        }
    }

    actual companion object {

        private const val TAG = "DateTimeUtils"

        actual val COMPLETE_DATE_TIME_FORMAT =
            "$DATE $MONTH_STRING_FULL $YEAR $HOUR_12:$MINUTES $AM_PM"

        actual val HOURS_MIN_FORMAT = "HH:mm"
        actual val DAY_DATE_FORMAT = "$DAY_SHORT $DATE"
    }

}
