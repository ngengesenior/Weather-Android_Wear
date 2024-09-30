package com.ngengeapps.weather.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
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
fun LatestWeather(response: Response<OneCallResponse>) {
    var timeZone: String by remember {
        mutableStateOf("")
    }
    when (response) {
        is Response.Uninitialized, is Response.Loading -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Response.Success -> {
            timeZone = response.data!!.timezone
            WeatherUI(
                modifier = Modifier.fillMaxSize(),
                response = response.data,
            )
        }

        is Response.Error<*> -> {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(response.message ?: "Error occurred")
            }
        }
    }

}


@Composable
fun WeatherUI(
    modifier: Modifier = Modifier,
    response: OneCallResponse,
    city: String = "Oklahoma City",

    ) {
    ScalingLazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        item {
            ListHeader {
                Text(city)
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





