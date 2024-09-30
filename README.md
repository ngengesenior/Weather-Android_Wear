## Weather Wear OS App.

Weather Wear OS App is a sort of toy weather project built for Android Wear OS mimicking the stock
weather app that comes with Google Watch.
Inspiration was just gotten after watching Philipp Lackner's Youtube tutorial and as well after
purchasing a Pixel watch 2. In summary, the app does the following

- Requests user's current location using Google Play Services API
- Gets weather data from OpenWeather API.
- Geocodes the gotten longitude and latitude using Geocoder API to get the current locality
- Display the current temperatures and next three hours temperatures
- Clicking the current weather brings the details which include, the City name, temperature readings
  for next 7 days and next 8 hours

### Tech Stack

- Jetpack Compose for UI and navigation
- [Kamel](https://github.com/Kamel-Media/Kamel) for Image loading
- [Open Weather API](https://openweathermap.org/api) for weather data
- [Ktor](https://ktor.io/) for API calls
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON parsing
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency
  injection

## Screenshots And Video

![home](/screenshots/home.png)

![next_hours](/screenshots/next_hours.png)

![next_days](/screenshots/next_days.png)

## Sample Recording

![recording](/screenshots/record.mp4)
