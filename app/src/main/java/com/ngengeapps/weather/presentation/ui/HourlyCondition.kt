package com.ngengeapps.weather.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.data.HourlyWeather
import com.ngengeapps.weather.presentation.ui.common.ConditionRow
import com.ngengeapps.weather.presentation.utils.formatToAmPm
import com.ngengeapps.weather.presentation.utils.utcDateTimeToLocalDateTime
import kotlin.math.roundToInt

@Composable
fun HourConditionRow(hourlyWeather: HourlyWeather, timeZone: String) {
    ConditionRow(
        modifier = Modifier.fillMaxWidth(),
        lefText = utcDateTimeToLocalDateTime(hourlyWeather.dt, timeZone).formatToAmPm(),
        iconCode = hourlyWeather.weather[0].icon,
        rightText = buildAnnotatedString {
            append("${hourlyWeather.temp.roundToInt()}°")
        })

}


fun ScalingLazyListScope.hourConditionColumn(
    hourlyWeather: List<HourlyWeather>,
    timeZone: String
) {
    item {
        ListHeader {
            Text("Next 8 hours")
        }
    }

    items(hourlyWeather.size) { index ->
        HourConditionRow(hourlyWeather[index], timeZone)
    }
}