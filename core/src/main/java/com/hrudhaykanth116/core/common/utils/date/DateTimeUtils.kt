package com.hrudhaykanth116.weathertens.common.utils.date

import java.text.SimpleDateFormat
import java.util.*
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

    fun getTimeFromSecs(seconds: Int): String{
        // TODO: Use better formatter
        val formatter = SimpleDateFormat("HH:mm:ss")
        return formatter.format(Date(seconds * 1000L))
    }

}