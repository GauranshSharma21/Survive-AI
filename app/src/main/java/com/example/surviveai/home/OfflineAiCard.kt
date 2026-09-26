package com.example.surviveai.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OfflineAiCard(
    onOpenAiClick: () -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text(
                text = "🤖 OFFLINE AI ASSISTANT",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Get survival guidance offline",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = onOpenAiClick,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "OPEN AI"
                )
            }
        }
    }
}

