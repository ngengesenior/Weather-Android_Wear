package com.ngengeapps.weather.presentation.data

import android.content.Context
import android.location.Geocoder
import com.ngengeapps.weather.presentation.AndroidGeocodingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideWeatherClient(geocodingService: AndroidGeocodingService): WeatherAPIClient {
        return WeatherAPIClient(geocodingService)
    }


}