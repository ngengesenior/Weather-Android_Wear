package com.ngengeapps.weather.presentation.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
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
import com.ngengeapps.weather.presentation.utils.shouldShowRequestRationale

private val permissions = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

@Composable
fun LocationPermissionUI(
    onPermissionGranted: (usePreciseLocation: Boolean) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current as ComponentActivity
    var shouldShowRationale by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }
    val permissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permsMap ->
            val fineLocationGranted = permsMap.getOrDefault(ACCESS_FINE_LOCATION, false)
            val coarseLocationGranted = permsMap.getOrDefault(ACCESS_COARSE_LOCATION, false)
            if (fineLocationGranted || coarseLocationGranted) {
                onPermissionGranted(fineLocationGranted)
            } else {
                onPermissionDenied()
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
        RationalDialog(modifier = Modifier.fillMaxSize(),
            onAcceptToRequestPermission = {
                permissionsLauncher.launch(permissions)
                permissionRequested = true
                shouldShowRationale = false
            },
            onDenyToContinue = {
                shouldShowRationale = false
            })
    }


}

@Composable
private fun RationalDialog(
    modifier: Modifier = Modifier,
    onAcceptToRequestPermission: () -> Unit,
    onDenyToContinue: () -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        var showDialog: Boolean by remember { mutableStateOf(true) }

        AlertDialog(
            show = showDialog,
            title = { Text("Share Location") },
            text = {
                Text("Please grant location permissions to show weather")
            },
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                AlertDialogDefaults.ConfirmButton(
                    onClick = {
                        showDialog = false
                        onAcceptToRequestPermission()
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