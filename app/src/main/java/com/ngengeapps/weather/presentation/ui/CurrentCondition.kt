package com.ngengeapps.weather.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material.icons.sharp.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.data.OneCallResponse
import kotlin.math.roundToInt

@Composable
fun CurrentCondition(
    modifier: Modifier = Modifier,
    apiResponse: OneCallResponse,
) {
    if (!apiResponse.daily.isNullOrEmpty()) {

        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Sharp.ArrowUpward,
                    contentDescription = null,
                    tint = Color.Red
                )
                WeatherIcon(
                    modifier = Modifier.size(40.dp),
                    iconCode = apiResponse.current.weather[0].icon
                )
                Icon(
                    imageVector = Icons.Sharp.ArrowDownward,
                    contentDescription = null,
                    tint = Color.Blue
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("${apiResponse.daily[0].temp.max.roundToInt()}°")
                Text("${apiResponse.current.temp}°")
                Text("${apiResponse.daily[0].temp.min.roundToInt()}°")

            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Current UV Index",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "UV ${apiResponse.current.uvi.roundToInt()}",
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),

                )
        }

    }

}