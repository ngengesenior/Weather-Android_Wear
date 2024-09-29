package com.ngengeapps.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngengeapps.weather.presentation.data.OneCallResponse
import com.ngengeapps.weather.presentation.data.Response
import com.ngengeapps.weather.presentation.data.WeatherAPIClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherClient: WeatherAPIClient):ViewModel() {
    private val _response: MutableStateFlow<Response<OneCallResponse>> = MutableStateFlow(Response<OneCallResponse>.Uninitialized())
    val response = _response.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            Response<OneCallResponse>.Uninitialized()
        )

    init {
        fetchWeather(latitude = 35.481918, longitude =-97.508469)
    }

    fun fetchWeather(latitude: Double,longitude: Double) {
        viewModelScope.launch{
            val weatherResponse = weatherClient.getWeatherResponse(latitude,longitude)
            _response.value = weatherResponse
        }
    }


}