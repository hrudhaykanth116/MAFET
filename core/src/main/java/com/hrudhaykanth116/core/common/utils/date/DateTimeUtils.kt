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

    // TODO: Use millis everywhere.
    fun getDateFromMillis(millis: Long?): String? {
        millis ?: return null

        // TODO: Use better formatter
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    // TODO: Remove this function
    fun getDateFromSecs(seconds: Int?): String? {
        seconds ?: return null

        // TODO: Use better formatter
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
        // TODO: Use better formatter
        val formatter = SimpleDateFormat(format)
        return formatter.format(Date(seconds * 1000L))
    }

    companion object {

        // const val COMPLETE_DATE_TIME_FORMAT = "EEEE, yyyy LLLL d, yyyy HH:mm:ss aaa"
        const val COMPLETE_DATE_TIME_FORMAT =
            "$YEAR-$MONTH_STRING_FULL-$DATE,$HOUR_12:$MINUTES:$SECONDS $AM_PM"

        const val HOURS_MIN_FORMAT = "HH:mm"
        const val DAY_DATE_FORMAT = "$DAY_SHORT $DATE"
    }

}