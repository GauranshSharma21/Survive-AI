package com.example.surviveai.sos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SOSScreen(
    onBack: () -> Unit,
    onActivateSOS: () -> Unit,
    onManageContacts: () -> Unit,
    locationMessage: String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "🆘 SOS",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Emergency SOS",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Send an emergency message with your current location to your emergency contacts.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = locationMessage,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )

        Button(
            onClick = onActivateSOS,
            modifier = Modifier.padding(top = 24.dp)
        ){
            Text(
                text = "ACTIVATE SOS"
            )
        }

        Button(
            onClick = onManageContacts,
            modifier = Modifier.padding(top = 12.dp)
        ){
            Text(
                text = "MANAGE EMERGENCY CONTACTS"
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
