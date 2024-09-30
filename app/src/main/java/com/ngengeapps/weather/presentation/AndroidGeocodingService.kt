package com.ngengeapps.weather.presentation

import android.location.Geocoder
import android.os.Build
import com.ngengeapps.weather.presentation.data.Place
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@Suppress("DEPRECATION")
class AndroidGeocodingService @Inject constructor(private val geocoder: Geocoder) {
    /*
    * Geocodes an address and returns the first result. returns full address
     */
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

    /*
    * Geocodes an address and returns the first result. returns full address
     */

    suspend fun geocodeAddress(latitude: Double, longitude: Double): Result<Place> {
        return suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
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
                        continuation.resume(Result.failure(Exception("Could not geocode address for $latitude, $longitude")))
                    }
                }
            } else {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val firstAddress = addresses[0]
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
                    continuation.resume(Result.failure(Exception("Could not geocode address for $latitude, $longitude")))
                }
            }


        }
    }
}