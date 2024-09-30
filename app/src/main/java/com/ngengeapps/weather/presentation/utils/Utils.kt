package com.ngengeapps.weather.presentation.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun utcDateTimeToLocalDateTime(dt: Long, timeZoneId: String): LocalDateTime {
    val timeZone = TimeZone.of(timeZoneId)
    return Instant.fromEpochSeconds(dt).toLocalDateTime(timeZone)
}

fun LocalDateTime.formatToDayAndMonth(): String = format(
    LocalDateTime.Format {
        dayOfMonth()
        char(' ')
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')

    }
)

fun LocalDateTime.formatToAmPm(): String = format(
    LocalDateTime.Format {
        amPmHour(padding = Padding.NONE)
        chars(" ")
        amPmMarker("AM", "PM")
    }
)


fun LocalDateTime.formatToDayString(): String = format(
    LocalDateTime.Format {
        dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
    }
)

fun LocalDateTime.formatToHourMinute(): String = format(
    LocalDateTime.Format {
        amPmHour(padding = Padding.NONE)
        char(':')
        minute()
        amPmMarker("AM", "PM")
    }
)

fun iconUrlFromCode(code: String): String = "https://openweathermap.org/img/wn/$code@2x.png"

fun getUVText(uv: Double): String {

    return when (uv.toInt()) {
        in 0..2 -> "Low"
        in 3..5 -> "Moderate"
        in 6..7 -> "High"
        in 8..10 -> "Very High"
        in 11..Int.MAX_VALUE -> "Extreme"
        else -> {
            "Unknown"
        }
    }
}

fun anyPermissionGranted(context: Context, permissions: Array<String>): Boolean {
    return permissions.any {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}

fun shouldShowRequestRationale(context: Context, permission: String): Boolean {
    return if (context is ComponentActivity) {
        context.shouldShowRequestPermissionRationale(permission)
    } else false

}


fun shouldShowRequestRationale(context: Context, permissions: Array<String>): Boolean {
    return permissions.any {
        shouldShowRequestRationale(context, it)
    }
}


