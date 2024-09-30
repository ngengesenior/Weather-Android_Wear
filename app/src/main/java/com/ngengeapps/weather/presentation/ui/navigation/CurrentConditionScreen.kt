package com.ngengeapps.weather.presentation.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.ngengeapps.weather.presentation.WeatherViewModel
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.ui.CurrentConditionAndNextThreeHours

@Composable
fun CurrentConditionScreen(
    viewModel: WeatherViewModel,
    onNavigateToDetails: (data: OneCallResponse) -> Unit = {}
) {
    val response: Response<OneCallResponse> by viewModel.response.collectAsStateWithLifecycle()
    Scaffold(timeText = { TimeText() }) {
        when (response) {
            is Response.Success<OneCallResponse> -> {
                CurrentConditionAndNextThreeHours(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = { onNavigateToDetails(response.data!!) }),
                    current = response.data!!.current,
                    daily = response.data!!.daily!![0],
                    hourly = response.data!!.hourly?.drop(1)?.take(3) ?: emptyList(),
                    timeZone = response.data!!.timezone,
                )

            }

            else -> CircularProgressIndicator()
        }


    }

}