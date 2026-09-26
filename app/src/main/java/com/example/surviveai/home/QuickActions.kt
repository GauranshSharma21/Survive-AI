package com.example.surviveai.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuickActions(
    onSosClick: () -> Unit,
    onFlashlightClick: () -> Unit,
    onSirenClick: () -> Unit,
    onMapsClick: () -> Unit
){
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),

    verticalArrangement = Arrangement.spacedBy(8.dp)
){
    //First row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Card(
            modifier = Modifier.weight(1f),
            onClick = onSosClick
        ){
            Column(
                modifier = Modifier.padding(20.dp)
            ){
                Text(text = "🆘")
                Text(text = "SOS")
                Text(text = "Emergency")
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            onClick = onFlashlightClick
        ){
            Column(
                modifier = Modifier.padding(20.dp)
            ){
                Text(text = "🔦")
                Text(text = "Flashlight")
            }
        }
    }

    //Second Row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Card(
            modifier = Modifier.weight(1f),
            onClick = onSirenClick
        ){
            Column(
                modifier = Modifier.padding(20.dp)
            ){
                Text(text = "🔊")
                Text(text = "Siren")
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            onClick = onMapsClick
        ){
            Column(
                modifier = Modifier.padding(20.dp)
            ){
                Text(text = "🗺️")
                Text(text = "Maps")
            }
        }
    }
}
}