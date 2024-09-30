package com.ngengeapps.weather.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.ngengeapps.weather.presentation.WeatherViewModel

private const val detailsRoute = "details"
private const val homeRoute = "home"

@Composable
fun WeatherNavigation(navController: NavHostController, sharedVm: WeatherViewModel) {
    SwipeDismissableNavHost(
        navController = navController, startDestination = homeRoute
    ) {
        composable(homeRoute) {
            CurrentConditionScreen(viewModel = sharedVm, onNavigateToDetails = { data ->
                sharedVm.putCurrentSuccessResponse(data)
                navController.navigate(detailsRoute)
            })
        }
        composable(detailsRoute) {
            WeatherDetailsScreen(sharedVm)
        }

    }
}

