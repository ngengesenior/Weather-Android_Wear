package com.ngengeapps.weather.presentation.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Scaffold
import com.ngengeapps.weather.presentation.WeatherViewModel
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.ui.WeatherUI


@Composable
fun WeatherDetailsScreen(viewModel: WeatherViewModel) {
    val response by viewModel.currentSuccessResponse.collectAsStateWithLifecycle()
    Scaffold {
        response?.let {
            WeatherDetailsScreen(it)
        }
    }
}

@Composable
fun WeatherDetailsScreen(response: OneCallResponse) {
    WeatherUI(response = response, modifier = Modifier.fillMaxSize())
}