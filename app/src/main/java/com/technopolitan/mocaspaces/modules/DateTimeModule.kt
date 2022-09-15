package com.technopolitan.mocaspaces.modules

import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import dagger.Module
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


enum class DateTimeUnits {
    DAYS,
    SECONDS,
    MINUTES,
    HOURS,
    MILLISECONDS,
    Years;
}

@Module
class DateTimeModule {

    fun convertDate(
        date: String?,
        fromFormat: String?,
        toFormat: String = DateTimeConstants.apiDefaultDateTimeFormat
    ): String? {
        var date = date
        val inFormat = SimpleDateFormat(toFormat, Locale.US)
        try {
            date = if (fromFormat == null) {
                SimpleDateFormat(
                    DateTimeConstants.apiDefaultDateTimeFormat,
                    Locale.ENGLISH
                ).parse(date!!)?.let {
                    inFormat.format(
                        it
                    )
                }
            } else {
                SimpleDateFormat(fromFormat, Locale.ENGLISH).parse(date!!)
                    ?.let { inFormat.format(it) }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun addCurrentDayMonthYearToDate(
        date: Date
    ): Date {
        var date: Date = date
        try {
            val currentDateCalender = GregorianCalendar()
            val timeCalender = GregorianCalendar()
            currentDateCalender.time = Calendar.getInstance().time
            timeCalender.time = date
            currentDateCalender.set(
                currentDateCalender.get(Calendar.YEAR),
                currentDateCalender.get(Calendar.MONTH),
                currentDateCalender.get(Calendar.DAY_OF_MONTH),
                timeCalender.get(Calendar.HOUR_OF_DAY),
                timeCalender.get(Calendar.MINUTE)
            )
            date = currentDateCalender.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun convertMilliToDate(timeInSecond: String, format: String?): String? {
        val time = timeInSecond.trim { it <= ' ' }.toLong()
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = time
        val formatter: DateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        return formatter.format(calendar.time)
    }

    fun getTimeInMilli(isUseTimeUTC: Boolean = false): String {
        val calendar =
            if (isUseTimeUTC) Calendar.getInstance(TimeZone.getTimeZone("UTC")) else Calendar.getInstance()
        val currentLocalTime = calendar.timeInMillis
        return currentLocalTime.toString()
    }

    fun getTodayDateOrTime(format: String): String? {
        return convertDate(
            Calendar.getInstance().time.toString(),
            DateTimeConstants.defaultDateTimeFormatOfAndroid, format
        )
    }

    fun diffInDates(
        startDate: Date,
        endDate: Date,
        dateTimeUnits: DateTimeUnits = DateTimeUnits.HOURS
    ): Int {
        val diffInMs: Long = startDate.time - endDate.time
        val days = TimeUnit.MILLISECONDS.toDays(diffInMs).toInt()
        val hours =
            (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days.toLong())).toInt()
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(diffInMs)
        )).toInt()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs).toInt()
        return when (dateTimeUnits.name) {
            DateTimeUnits.DAYS.name -> days
            DateTimeUnits.SECONDS.name -> seconds
            DateTimeUnits.MINUTES.name -> minutes
            DateTimeUnits.MILLISECONDS.name -> diffInMs.toInt()
            DateTimeUnits.HOURS.name -> hours
            DateTimeUnits.Years.name -> days / 365
            else -> diffInMs.toInt()
        }
//        return when (dateTimeUnits) {
//             ->
//            SECONDS -> seconds
//            MINUTES -> minutes
//            HOURS -> hours
//            MILLISECONDS -> diffInMs.toInt()
//            else -> diffInMs.toInt()
//        }
    }

    fun formatDate(date: String, format: String): Date? {
        return SimpleDateFormat(
            format,
            Locale.ENGLISH
        ).parse(date)
    }
}