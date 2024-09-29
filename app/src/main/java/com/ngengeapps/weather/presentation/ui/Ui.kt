package com.ngengeapps.weather.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material.icons.sharp.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.ngengeapps.weather.presentation.WeatherViewModel
import com.ngengeapps.weather.presentation.data.HourlyWeather
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.data.WeatherAPIClient
import com.ngengeapps.weather.presentation.utils.formatToAmPm
import com.ngengeapps.weather.presentation.utils.iconUrlFromCode
import com.ngengeapps.weather.presentation.utils.utcDateTimeToLocalDateTime
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch

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
    Scaffold(timeText = {TimeText()}) {
        when(response) {
            is Response.Uninitialized,is Response.Loading -> {
                Box(Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center ) {
                    CircularProgressIndicator()
                }
            }

            is Response.Success -> {
                timeZone = response.data!!.timezone
                WeatherUI(modifier = Modifier.fillMaxSize(),response = response.data, timeZone = timeZone)
            }

            is Response.Error<*> -> {
                Box(Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center ) {
                    Text(response.message?: "Error occurred")
                }
            }
        }
    }

}

@Composable fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val response by viewModel.response.collectAsStateWithLifecycle()
    LatestWeather(response)
}

@Composable
fun HourConditionUI(hourlyWeather: HourlyWeather, timeZone: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(utcDateTimeToLocalDateTime(hourlyWeather.dt, timeZone).formatToAmPm())
        WeatherIcon(modifier = Modifier.size(45.dp),hourlyWeather.weather[0].icon)
        Text("${hourlyWeather.temp}°")
    }
}


@Composable
fun WeatherUI(modifier: Modifier = Modifier, response: OneCallResponse, city: String = "Oklahoma City", timeZone: String) {
    ScalingLazyColumn(modifier = modifier) {
        item {
            ListHeader {
                Text(city)
            }
        }
        item {
            CurrentCondition(modifier = Modifier.fillMaxWidth(),response)
        }
        items(items = response.hourly!!) { hourlyWeather->
            HourConditionUI(hourlyWeather, timeZone = timeZone )
        }
    }

}


@Composable
fun CurrentCondition(
    modifier: Modifier = Modifier,
    apiResponse: OneCallResponse,
) {
    if (!apiResponse.daily.isNullOrEmpty()) {

        Column(modifier = modifier) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Sharp.ArrowUpward,contentDescription = null, tint = Color.Red)
                WeatherIcon(modifier = Modifier.size(40.dp),
                    iconCode = apiResponse.current.weather[0].icon )
                Icon(imageVector = Icons.Sharp.ArrowDownward,contentDescription = null, tint = Color.Blue)

            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                Text("${apiResponse.daily[0].temp.max}°")
                Text("${apiResponse.current.temp}°")
                Text("${apiResponse.daily[0].temp.min}°")

            }
        }
    }

}


