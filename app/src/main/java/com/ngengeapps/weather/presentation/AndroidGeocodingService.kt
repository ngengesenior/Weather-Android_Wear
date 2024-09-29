package com.ngengeapps.weather.presentation

import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.ngengeapps.weather.presentation.data.Place
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

@Suppress("DEPRECATION")
class AndroidGeocodingService @Inject constructor(private val geocoder: Geocoder){
    //private val geocoder = Geocoder(context, Locale.getDefault())
    suspend fun geocodeAddress(address: String): Result<Place> {
        return suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(address, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val firstAddress = addresses[0]
                        firstAddress.locality
                        continuation.resume(
                            Result.success(
                                Place(
                                    firstAddress.latitude,
                                    firstAddress.longitude,
                                    country = firstAddress.countryName,
                                    zipCode = firstAddress.postalCode,
                                    name = firstAddress.locality,
                                    locality = firstAddress.locality,
                                )
                            )
                        )
                    } else {
                        continuation.resume(Result.failure(Exception("Could not get coordinates for $address")))
                    }
                }
            } else {
                val addresses = geocoder.getFromLocationName(address, 1)
                if (!addresses.isNullOrEmpty()) {
                    val firstAddress = addresses[0]
                    continuation.resume(
                        Result.success(
                            Place(
                                firstAddress.latitude,
                                firstAddress.longitude
                            )
                        )
                    )
                } else {
                    continuation.resume(Result.failure(Exception("Could not get coordinates for $address")))
                }
            }


        }
    }
}