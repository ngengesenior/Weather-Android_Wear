package com.ngengeapps.weather.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import com.ngengeapps.weather.presentation.ui.WeatherIcon

@Composable
fun ConditionRow(
    modifier: Modifier = Modifier,
    lefText: String,
    iconCode: String,
    rightText: AnnotatedString
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(lefText)
        WeatherIcon(modifier = Modifier.size(45.dp), iconCode)
        Text(rightText)
    }
}