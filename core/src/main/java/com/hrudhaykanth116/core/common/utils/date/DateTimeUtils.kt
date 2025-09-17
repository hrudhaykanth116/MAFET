package com.hrudhaykanth116.core.common.utils.date

import android.icu.util.Calendar
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DAY_SHORT
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.SECONDS
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeUtils @Inject constructor(

) {

    fun getDateFromMillis(millis: Long?): String? {
        millis ?: return null

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun getDateFromSecs(seconds: Int?): String? {
        seconds ?: return null
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(seconds * 1000L))
    }

    fun dateFromUTC(date: Date): Long {
        return Date(date.time + Calendar.getInstance().timeZone.getOffset(Date().time)).time
    }

    fun dateToUTC(millis: Long): Long {
        val date = Date(millis)
        return Date(millis - Calendar.getInstance().timeZone.getOffset(date.time)).time
    }

    fun getTimeFromSecs(
        seconds: Int?,
        format: String = "HH:mm:ss",
    ): String? {

        seconds ?: return null
        val formatter = SimpleDateFormat(format)
        return formatter.format(Date(seconds * 1000L))
    }

    fun getFormattedDateTime(timeMillis: Long, pattern: String = COMPLETE_DATE_TIME_FORMAT): String{
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }

    fun getMillisFromDateTime(dateTime: String, pattern: String = COMPLETE_DATE_TIME_FORMAT): Long? {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.parse(dateTime)?.time
    }

    companion object {

        const val COMPLETE_DATE_TIME_FORMAT =
            "$DATE $MONTH_STRING_FULL $YEAR $HOUR_12:$MINUTES $AM_PM"

        const val HOURS_MIN_FORMAT = "HH:mm"
        const val DAY_DATE_FORMAT = "$DAY_SHORT $DATE"
    }

}