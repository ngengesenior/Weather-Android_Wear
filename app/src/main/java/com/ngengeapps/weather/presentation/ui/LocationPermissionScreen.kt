package com.ngengeapps.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material3.AlertDialog
import androidx.wear.compose.material3.AlertDialogDefaults
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.utils.anyPermissionGranted
import com.ngengeapps.weather.presentation.utils.shouldShowRequestRationale

private val permissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

@Composable
fun LocationPermissionUI(
    onPermissionGranted: (usePreciseLocation: Boolean) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    var shouldShowRationale by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }
    var permissionDeniedText: String? by remember { mutableStateOf(null) }
    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permsMap ->
        val fineLocationGranted = permsMap.getOrDefault(ACCESS_FINE_LOCATION, false)
        val coarseLocationGranted = permsMap.getOrDefault(ACCESS_COARSE_LOCATION, false)
        if (fineLocationGranted || coarseLocationGranted) {
            onPermissionGranted(fineLocationGranted)
        } else {
            onPermissionDenied()
            permissionDeniedText = "Location permission denied"
        }


    }

    /**
     * In case user denies location permission and still want to use the app, give them ability to change settings
     */
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val fineLocationGranted = anyPermissionGranted(context, arrayOf(ACCESS_FINE_LOCATION))
        val coarseLocationGranted =
            anyPermissionGranted(context, arrayOf(ACCESS_COARSE_LOCATION))
        if (fineLocationGranted || coarseLocationGranted) {
            onPermissionGranted(fineLocationGranted)
        }

    }

    val settingsIntent = remember {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.applicationContext.packageName, null)
        }
    }

    LaunchedEffect(Unit) {
        shouldShowRationale = shouldShowRequestRationale(context, permissions)
    }
    LaunchedEffect(shouldShowRationale, permissionRequested) {
        if (!permissionRequested && !shouldShowRationale) {
            permissionsLauncher.launch(permissions)
            permissionRequested = true
        }
    }

    if (shouldShowRationale) {
        ActionDialog(
            modifier = Modifier.fillMaxSize(),
            onAcceptToTakeAction = {
                permissionsLauncher.launch(permissions)
                permissionRequested = true
                shouldShowRationale = false
            },
            onDenyToContinue = {
                shouldShowRationale = false
            }, title = "Share Location?",
            body = "Please grant location permissions to show weather"
        )
    }

    permissionDeniedText?.let {
        ActionDialog(
            modifier = Modifier.fillMaxSize(),
            title = "Open Settings?",
            body = "Enable location permissions from settings",
            onDenyToContinue = {
                permissionDeniedText = null
                Toast.makeText(context, "Location permission denied.Closing app", Toast.LENGTH_LONG)
                    .show()
                (context as ComponentActivity).finish()
            },
            onAcceptToTakeAction = {
                settingsLauncher.launch(settingsIntent)
            }
        )
    }


}


@Composable
private fun ActionDialog(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    onAcceptToTakeAction: () -> Unit,
    onDenyToContinue: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        var showDialog: Boolean by remember { mutableStateOf(true) }

        AlertDialog(
            show = showDialog,
            title = {
                Text(title)
            },
            text = {
                Text(body)
            },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                AlertDialogDefaults.ConfirmButton(
                    onClick = {
                        showDialog = false
                        onAcceptToTakeAction()
                    })
            },
            dismissButton = {
                AlertDialogDefaults.DismissButton(
                    onClick = {
                        showDialog = false
                        onDenyToContinue()
                    })
            }

        )

    }
}