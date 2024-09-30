package com.ngengeapps.weather.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.data.CurrentWeather
import com.ngengeapps.weather.presentation.data.DailyWeather
import com.ngengeapps.weather.presentation.data.HourlyWeather
import com.ngengeapps.weather.presentation.utils.formatToAmPm
import com.ngengeapps.weather.presentation.utils.utcDateTimeToLocalDateTime
import kotlin.math.roundToInt

@Composable
fun CurrentConditionAndNextThreeHours(
    modifier: Modifier = Modifier,
    current: CurrentWeather,
    daily: DailyWeather,
    hourly: List<HourlyWeather>,
    timeZone: String,
    city: String
) {

    ScalingLazyColumn(modifier = modifier) {
        item {
            Text(city, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(Modifier.height(10.dp))
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherIcon(modifier = Modifier.size(50.dp), current.weather[0].icon)
                Text(
                    "${current.temp.roundToInt()}°",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = buildAnnotatedString {
                    append("${daily.temp.max.roundToInt()}°")
                    append("\n")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                        )
                    ) {
                        append("${daily.temp.min.roundToInt()}°")
                    }
                })
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (hour in hourly) {
                    Column {
                        Text("${hour.temp.roundToInt()}°")
                        WeatherIcon(
                            iconCode = hour.weather[0].icon,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(utcDateTimeToLocalDateTime(hour.dt, timeZone).formatToAmPm())
                    }

                }

            }
        }
    }

}


