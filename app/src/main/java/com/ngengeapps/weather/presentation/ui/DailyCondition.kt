package com.ngengeapps.weather.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import com.ngengeapps.weather.presentation.data.DailyWeather
import com.ngengeapps.weather.presentation.ui.common.ConditionRow
import com.ngengeapps.weather.presentation.ui.common.TitleText
import com.ngengeapps.weather.presentation.utils.formatToDayString
import com.ngengeapps.weather.presentation.utils.utcDateTimeToLocalDateTime
import kotlin.math.roundToInt

@Composable
fun DailyConditionRow(dailyWeather: DailyWeather, timeZone: String) {
    ConditionRow(
        modifier = Modifier.fillMaxWidth(),
        lefText = utcDateTimeToLocalDateTime(
            dailyWeather.dt,
            timeZone
        ).formatToDayString(),
        iconCode = dailyWeather.weather[0].icon, rightText = buildAnnotatedString {
            append("${dailyWeather.temp.max.roundToInt()}°")
            append("/")
            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                )
            ) {
                append("${dailyWeather.temp.min.roundToInt()}°")

            }
        })
}


fun ScalingLazyListScope.dailyConditionColumn(dailyWeather: List<DailyWeather>, timeZone: String) {
    item {
        ListHeader {
            TitleText(text = "Next 7 days")
        }
    }
    items(dailyWeather.size) { index ->
        DailyConditionRow(dailyWeather[index], timeZone)
    }


}