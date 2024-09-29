/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.ngengeapps.weather.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Text
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.data.WeatherAPIClient
import com.ngengeapps.weather.presentation.location.LocationServiceUtil
import com.ngengeapps.weather.presentation.theme.WeatherTheme
import com.ngengeapps.weather.presentation.ui.LatestWeather
import com.ngengeapps.weather.presentation.ui.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)
        setContent {
            WeatherTheme {
                WeatherScreen()
            }

            
        }
    }
}



@OptIn(ExperimentalCoroutinesApi::class)
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
    var response: Response<OneCallResponse> by remember {
        mutableStateOf(Response.Uninitialized())
    }

    val permissionsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permsMap ->
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
                    /*response = apiClient.getWeatherResponse(
                        coordinates.latitude,
                        coordinates.longitude
                    )*/


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
                Toast.makeText(context, "Please grant location permissions to see weather info", Toast.LENGTH_LONG)
                    .show()
                permissionsLauncher.launch(arrayOf(ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION))
            }

        }


    }

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Hello")
        Text(latLang)

    }

}



fun anyPermissionGranted(context: Context, permissions: List<String>): Boolean {
    return permissions.any {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}

fun shouldShowRequestRationale(context: Context, permission: String): Boolean {
    return if (context is ComponentActivity) {
        context.shouldShowRequestPermissionRationale(permission)
    } else false

}



