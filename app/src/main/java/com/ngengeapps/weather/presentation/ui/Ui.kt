package com.ngengeapps.weather.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.ListHeader
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.ui.common.TitleText
import com.ngengeapps.weather.presentation.utils.iconUrlFromCode
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun WeatherIcon(modifier: Modifier = Modifier, iconCode: String) {
    Box(modifier = modifier) {
        KamelImage(
            resource = asyncPainterResource(iconUrlFromCode(iconCode)),
            contentDescription = null, animationSpec = tween(),
            contentScale = ContentScale.Inside
        )
    }
}


@Composable
fun WeatherUI(
    modifier: Modifier = Modifier,
    response: OneCallResponse,
    city: String,

    ) {
    ScalingLazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            ListHeader {
                TitleText(text = city)
            }
        }
        item {
            CurrentCondition(modifier = Modifier.fillMaxWidth(), response)
        }
        hourConditionColumn(hourlyWeather = response.hourly!!.drop(1).take(8), response.timezone)
        item {
            Spacer(Modifier.height(16.dp))
        }
        dailyConditionColumn(dailyWeather = response.daily!!.drop(1).take(7), response.timezone)
    }

}





