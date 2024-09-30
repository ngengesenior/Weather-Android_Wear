package com.ngengeapps.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.data.WeatherAPIClient
import com.ngengeapps.weather.presentation.location.LocationServiceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherClient: WeatherAPIClient,
    private val locationServiceUtil: LocationServiceUtil,
    private val geocodingService: AndroidGeocodingService
) :
    ViewModel() {
    private val _response: MutableStateFlow<Response<OneCallResponse>> =
        MutableStateFlow(Response<OneCallResponse>.Uninitialized())
    val response = _response.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Response<OneCallResponse>.Uninitialized()
        )
    private val _currentSuccessResponse = MutableStateFlow<OneCallResponse?>(null)
    val currentSuccessResponse = _currentSuccessResponse.asStateFlow()

    private val _locality = MutableStateFlow<String?>(null)
    val locality = _locality.asStateFlow()

    init {
        //fetchWeather(latitude = 35.481918, longitude = -97.508469)
    }

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherResponse = weatherClient.getWeatherResponse(latitude, longitude)
            _response.value = weatherResponse
        }
    }


    fun getCurrentLocationAndFetchWeather(usePreciseLocation: Boolean) {
        viewModelScope.launch {
            val locationResponse = locationServiceUtil.getCurrentLocation(usePreciseLocation)
            locationResponse.onSuccess {
                fetchWeather(it.latitude, it.longitude)
                val geoCodeResponse = geocodingService.geocodeAddress(it.latitude, it.longitude)
                geoCodeResponse.onSuccess {
                    _locality.value = it.locality
                }
                geoCodeResponse.onFailure {
                    _locality.value = "Your location"
                }
            }
            locationResponse.onFailure {
                _response.value = Response.Error("Error getting current location")
            }

        }


    }

    fun putCurrentSuccessResponse(response: OneCallResponse) {
        _currentSuccessResponse.value = response
    }

}