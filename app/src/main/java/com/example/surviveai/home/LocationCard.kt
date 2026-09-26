package com.example.surviveai.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationCard(
    locationStatus: String,
    locationText: String,
    locationPermissionGranted : Boolean,
    onEnableLocation: () -> Unit
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text(
                text = "📍 Your Location"
            )

            Text(
                text = locationStatus
            )

            Text(
                text = locationText
            )

            Button(
                onClick = onEnableLocation

            ){
                Text(
                    text = if (locationPermissionGranted) {
                        "Refresh Location"
                    } else {
                        "Enable Location"
                    }
                )
            }
        }
    }
}