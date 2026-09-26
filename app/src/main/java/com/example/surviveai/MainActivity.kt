package com.example.surviveai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.surviveai.disaster.DisasterModeScreen
import com.example.surviveai.home.SurviveAiHomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface {
                    var disasterModeActive by remember{
                        mutableStateOf(false)
                    }

                    if(disasterModeActive){
                        DisasterModeScreen(
                        onExit = {
                            disasterModeActive = false
                        }
                        )
                    }else{
                        SurviveAiHomeScreen(
                            onDisasterModeActivate = {
                                disasterModeActive = true
                            }
                        )
                    }
                }
            }
        }
    }
}