package com.ngengeapps.weather.presentation.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import com.ngengeapps.weather.presentation.WeatherViewModel
import com.ngengeapps.weather.presentation.ui.LocationPermissionUI

@Composable
fun LocationPermissionScreen(sharedVm: WeatherViewModel, onNavigate: () -> Unit) {
    val context = LocalContext.current
    Scaffold(timeText = { TimeText() }) {
        LocationPermissionUI(onPermissionGranted = { usePreciseLocation ->
            sharedVm.getCurrentLocationAndFetchWeather(usePreciseLocation)
            onNavigate()
        }, onPermissionDenied = {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_LONG).show()
        })
    }
}