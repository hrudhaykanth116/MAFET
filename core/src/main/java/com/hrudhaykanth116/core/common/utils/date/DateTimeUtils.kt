package com.hrudhaykanth116.core.common.utils.date

import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DAY_SHORT
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR
import com.hrudhaykanth116.core.common.utils.log.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeUtils @Inject constructor(

) {

    fun getFormattedDateTime(
        timeMillis: Long?,
        pattern: String = COMPLETE_DATE_TIME_FORMAT,
    ): String? {

        timeMillis ?: return null

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }

    fun getMillisFromDateTime(
        dateTime: String?,
        pattern: String = COMPLETE_DATE_TIME_FORMAT,
    ): Long? {

        if (dateTime.isNullOrBlank()) return null

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            sdf.parse(dateTime)?.time
        } catch (exception: Exception) {
            Logger.e(TAG, "getMillisFromDateTime: ", exception)
            null
        }
    }

    companion object {

        private const val TAG = "DateTimeUtils"

        const val COMPLETE_DATE_TIME_FORMAT =
            "$DATE $MONTH_STRING_FULL $YEAR $HOUR_12:$MINUTES $AM_PM"

        const val HOURS_MIN_FORMAT = "HH:mm"
        const val DAY_DATE_FORMAT = "$DAY_SHORT $DATE"
    }

}