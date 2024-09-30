/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.ngengeapps.weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.ngengeapps.weather.presentation.theme.WeatherTheme
import com.ngengeapps.weather.presentation.ui.navigation.WeatherNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    val sharedVm: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            val navController = rememberSwipeDismissableNavController()
            WeatherTheme {
                WeatherNavigation(navController = navController, sharedVm)
            }
        }
    }
}


/*@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun WearApp(locationServiceUtil: LocationServiceUtil) {
    val context = LocalContext.current

    var latLang by remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    var usePreciseLocation by remember {
        mutableStateOf(false)
    }

    val permissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permsMap ->
            val fineLocationGranted = permsMap.getOrDefault(ACCESS_FINE_LOCATION, false)
            if (anyPermissionGranted(
                    context,
                    listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                )
            ) {
                //response = Response.Loading()
                usePreciseLocation = fineLocationGranted
                coroutineScope.launch {
                    val result = locationServiceUtil.getCurrentLocation(usePreciseLocation)
                    if (result.isSuccess) {
                        val coordinates = result.getOrNull()!!
                        latLang = "Lat:${coordinates.latitude},Lon:${coordinates.longitude}"
                        *//*response = apiClient.getWeatherResponse(
                            coordinates.latitude,
                            coordinates.longitude
                        )*//*


                    } else if (result.isFailure) {
                        latLang = "Result:${result.exceptionOrNull()?.message}"
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Please grant location permissions to show weather",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

    LaunchedEffect(Unit) {
        val finePermission = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
        val fineLocationGranted = finePermission == PackageManager.PERMISSION_GRANTED

        if (anyPermissionGranted(
                context,
                listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
            )
        ) {
            usePreciseLocation = fineLocationGranted
            coroutineScope.launch {
                val result = locationServiceUtil.getCurrentLocation(usePreciseLocation)
                if (result.isSuccess) {
                    val coords = result.getOrNull()
                    latLang = "Lat:${coords?.latitude},Lon:${coords?.longitude}"

                    // Get the currentWeather here

                }


            }


        } else {
            val shouldShowRationale = (shouldShowRequestRationale(
                context,
                ACCESS_COARSE_LOCATION
            ) || shouldShowRequestRationale(
                context,
                ACCESS_FINE_LOCATION
            ))
            if (shouldShowRationale) {
                Toast.makeText(
                    context,
                    "Please grant location permissions to see weather info",
                    Toast.LENGTH_LONG
                )
                    .show()
                permissionsLauncher.launch(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))
            }

        }


    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello")
        Text(latLang)

    }

}*/






