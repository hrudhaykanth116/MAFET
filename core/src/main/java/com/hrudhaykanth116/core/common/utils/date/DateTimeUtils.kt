package com.hrudhaykanth116.core.common.utils.date

import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.AM_PM
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.DATE
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.HOUR_12
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MINUTES
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.MONTH_STRING_FULL
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.SECONDS
import com.hrudhaykanth116.core.common.utils.date.DateTimeFormats.YEAR
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateTimeUtils @Inject constructor(

) {


    fun getDateFromSecs(seconds: Int): String {
        // TODO: Use better formatter
        val formatter = SimpleDateFormat("dd/MMM")
        return formatter.format(Date(seconds * 1000L))
    }

    fun getTimeFromSecs(seconds: Int): String {
        // TODO: Use better formatter
        val formatter = SimpleDateFormat("HH:mm:ss")
        return formatter.format(Date(seconds * 1000L))
    }

    companion object {

        // const val COMPLETE_DATE_TIME_FORMAT = "EEEE, yyyy LLLL d, yyyy HH:mm:ss aaa"
        const val COMPLETE_DATE_TIME_FORMAT =
            "$YEAR-$MONTH_STRING_FULL-$DATE,$HOUR_12:$MINUTES:$SECONDS $AM_PM"
    }

}