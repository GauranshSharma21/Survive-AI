package com.example.surviveai.flashlight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlashlightScreen(
  flashlightOn: Boolean,
  onToggleFlashlight: () -> Unit,
  onBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment  = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "🔦 FLASHLIGHT",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = if(flashlightOn){
                "Flashlight is ON"
            }else{
                "Flashlight is OFF"
            },
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Button(
            onClick = onToggleFlashlight,
            modifier = Modifier.padding(top = 24.dp)
        ){
            Text(
                text = if(flashlightOn){
                    "TURN OFF"
                }else{
                    "TURN ON"
                }
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 12.dp)
        ){
            Text(
                text = "BACK"
            )
        }
    }
}
