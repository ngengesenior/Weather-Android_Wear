package com.ngengeapps.weather.presentation.api

import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Place
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.location_services.AndroidGeocodingService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WeatherAPIClient @Inject constructor(private val geocodeService: AndroidGeocodingService) {

    companion object {
        const val OPEN_WEATHER_KEY = ""
        const val BASE_URL =
            "https://api.openweathermap.org/data/3.0/onecall?appid=${OPEN_WEATHER_KEY}&units=imperial"
    }

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

    }

    suspend fun getWeatherResponse(lat: Double, lon: Double): Response<OneCallResponse> {
        var result: Response<OneCallResponse> = Response.Loading()
        try {
            val response = client.get("$BASE_URL&lat=$lat&lon=$lon")
            if (response.status == HttpStatusCode.OK) {
                val decodedResponse = Json.decodeFromString<OneCallResponse>(response.body())
                result = Response.Success(decodedResponse)
            } else {
                result = Response.Error("Http Error: ${response.status.value}")
            }

        } catch (ex: Exception) {
            result = Response.Error("Error occurred while fetching weather data:${ex.message}")
        }

        return result
    }

    suspend fun getWeatherResponse(
        placeName: String,
    ): Response<Pair<OneCallResponse, Place?>> {
        var result: Response<Pair<OneCallResponse, Place?>> = Response.Loading()
        try {
            val geoCodeResult = geocodeService.geocodeAddress(placeName)
            if (geoCodeResult.isFailure) {
                return Response.Error(
                    message = geoCodeResult.exceptionOrNull()?.message
                        ?: "There was an error geocoding address"
                )
            }
            val place: Place = geoCodeResult.getOrNull()!!.copy(name = placeName)

            val weatherResponse = getWeatherResponse(place.latitude, place.longitude)
            result = if (weatherResponse is Response.Success) {
                Response.Success(Pair(weatherResponse.data!!, place))
            } else {
                Response.Error(
                    weatherResponse.message
                        ?: "There was an error getting response for place $placeName"
                )
            }

        } catch (ex: Exception) {
            result = Response.Error(
                message = ex.message ?: "There was an error getting response for place $placeName"
            )
        }

        return result
    }

}