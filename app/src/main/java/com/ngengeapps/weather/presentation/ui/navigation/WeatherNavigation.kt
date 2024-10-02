package com.ngengeapps.weather.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.ngengeapps.weather.presentation.data.WeatherViewModel

private const val detailsRoute = "details"
private const val homeRoute = "home"
private const val permissionsRoute = "permissions"

@Composable
fun WeatherNavigation(
    navController: NavHostController,
    sharedVm: WeatherViewModel
) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = permissionsRoute
    ) {
        composable(homeRoute) {
            CurrentConditionScreen(
                viewModel = sharedVm,
                onNavigateToDetails = { data ->
                    sharedVm.putCurrentSuccessResponse(data)
                    navController.navigate(detailsRoute)

                })
        }
        composable(detailsRoute) {
            WeatherDetailsScreen(sharedVm)
        }

        composable(permissionsRoute) {
            LocationPermissionScreen(sharedVm) {
                navController.navigate(homeRoute) {
                    popUpTo(permissionsRoute) { inclusive = true }
                }
            }
        }

    }
}

