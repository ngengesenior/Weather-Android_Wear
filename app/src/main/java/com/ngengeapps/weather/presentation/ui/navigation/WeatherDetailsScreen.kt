package com.ngengeapps.weather.presentation.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.WeatherViewModel
import com.ngengeapps.weather.presentation.ui.WeatherUI


@Composable
fun WeatherDetailsScreen(viewModel: WeatherViewModel) {
    val response by viewModel.currentSuccessResponse.collectAsStateWithLifecycle()
    val locality by viewModel.locality.collectAsStateWithLifecycle()
    Scaffold(timeText = { TimeText() }) {
        response?.let {
            WeatherDetailsScreen(it, locality = locality ?: "Your location")
        }
    }
}

@Composable
fun WeatherDetailsScreen(response: OneCallResponse, locality: String) {
    WeatherUI(response = response, modifier = Modifier.fillMaxSize(), city = locality)
}