package com.ngengeapps.weather.presentation.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ngengeapps.weather.presentation.api.WeatherAPIClient
import com.ngengeapps.weather.presentation.location_services.AndroidGeocodingService
import com.ngengeapps.weather.presentation.location_services.LocationServiceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providedGeoCodeService(geocoder: Geocoder): AndroidGeocodingService =
        AndroidGeocodingService(geocoder)

    @Provides
    fun provideGeoCoder(@ApplicationContext context: Context): Geocoder = Geocoder(context)

    @Provides
    @Singleton
    fun provideWeatherClient(geocodingService: AndroidGeocodingService): WeatherAPIClient =
        WeatherAPIClient(geocodingService)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun providedServiceUtil(locationClient: FusedLocationProviderClient): LocationServiceUtil =
        LocationServiceUtil(locationClient)

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


}