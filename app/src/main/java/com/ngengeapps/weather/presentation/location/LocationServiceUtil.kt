package com.ngengeapps.weather.presentation.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.ngengeapps.weather.presentation.data.Place
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class LocationServiceUtil(private val context: Context) {
    private val locationClient:FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(usePreciseLocation:Boolean):Result<Place> {
        val priority = if (usePreciseLocation) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY
        return suspendCancellableCoroutine { continuation ->
            locationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token
            )
                .addOnSuccessListener {
                    continuation.resume(Result.success(Place(it.latitude,it.longitude)))
                }.addOnFailureListener{
                    continuation.resume(Result.failure(it))
                }

        }
    }

}