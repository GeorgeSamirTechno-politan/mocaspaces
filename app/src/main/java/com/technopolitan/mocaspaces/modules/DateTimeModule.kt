package com.technopolitan.mocaspaces.modules

import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import dagger.Module
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Module
class DateTimeModule {

    fun convertDate(date: String?, fromFormat: String?, toFormat: String?): String? {
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

    fun getTodayDateOrTime(format: String?): String? {
        return convertDate(
            Calendar.getInstance().time.toString(),
            DateTimeConstants.defaultDateTimeFormatOfAndroid, format
        )
    }
}