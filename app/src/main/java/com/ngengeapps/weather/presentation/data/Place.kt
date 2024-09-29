package com.ngengeapps.weather.presentation.data

import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val latitude: Double,
    val longitude: Double,
    val name: String? = null,
    val zipCode: String? = null,
    val locality: String? = null,
    val country: String? = null
)