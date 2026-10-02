package com.example.surviveai

import android.Manifest
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.example.surviveai.disaster.DisasterModeScreen
import com.example.surviveai.home.SurviveAiHomeScreen
import com.example.surviveai.sos.SOSScreen
import com.example.surviveai.sos.EmergencyContactsScreen
import com.example.surviveai.sos.SOSLocationManager
import com.example.surviveai.sos.SOSMessageBuilder
import com.example.surviveai.sos.EmergencyContactStore
import com.example.surviveai.sos.SOSMessageSender
import com.example.surviveai.flashlight.FlashlightManager
import com.example.surviveai.flashlight.FlashlightScreen


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

                    var sosActive by remember{
                        mutableStateOf(false)
                    }
                    var emergencyContactsActive by remember{
                        mutableStateOf(false)
                    }

                    var flashlightActive by remember{
                        mutableStateOf(false)
                    }

                    var flashlightOn by remember{
                        mutableStateOf(false)
                    }

                    var sosLocationMessage by remember{
                        mutableStateOf("Location not retrieved yet")
                    }

                    val context = this@MainActivity

                    val sosLocationManager = remember{
                        SOSLocationManager(context)
                    }

                    val emergencyContactStore = remember {
                        EmergencyContactStore(context)
                    }

                    val sosMessageSender = remember {
                        SOSMessageSender(context)
                    }

                    val flashlightManager = remember {
                        FlashlightManager(context)
                    }

                    val locationPermissionLauncher =
                        rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestMultiplePermissions()
                        ) { permissions ->

                            val fineLocationGranted =
                                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

                            val coarseLocationGranted =
                                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

                            if (fineLocationGranted || coarseLocationGranted) {

                                sosLocationMessage = "Getting current location...."

                                sosLocationManager.getCurrentLocation(

                                    onLocationReceived = { location ->

                                        if (location != null) {

                                            val message =
                                                SOSMessageBuilder.buildMessage(location)

                                            sosLocationMessage = message

                                            val contacts =
                                                emergencyContactStore.getContacts()

                                            if (contacts.isEmpty()) {

                                                sosLocationMessage =
                                                    "No emergency contact saved"

                                            } else {

                                                // For now, use the first saved contact
                                                val firstContact = contacts.first()

                                                // Open the SMS app
                                                sosMessageSender.openSmsApp(
                                                    phoneNumber = firstContact.phoneNumber,
                                                    message = message,
                                                    onSmsAppNotFound = {

                                                        sosLocationMessage =
                                                            "No SMS app found on this device"
                                                    }
                                                )
                                            }

                                        } else {

                                            sosLocationMessage =
                                                "Unable to get location"
                                        }
                                    },

                                    onError = {

                                        sosLocationMessage =
                                            "Unable to get current location"
                                    }
                                )

                            } else {

                                sosLocationMessage =
                                    "Location permission is required"
                            }
                        }



                    if(disasterModeActive){
                        DisasterModeScreen(
                        onExit = {
                            disasterModeActive = false
                        }
                        )
                    }else if(sosActive){
                        SOSScreen(
                            onBack = {
                                sosActive = false
                            },
                            onActivateSOS = {

                                locationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            },
                            onManageContacts = {
                                sosActive = false
                                emergencyContactsActive = true
                            },
                            locationMessage = sosLocationMessage
                        )
                    }

                    else if(flashlightActive){

                        FlashlightScreen(

                            flashlightOn = flashlightOn,

                            onToggleFlashlight = {

                                val newState = !flashlightOn

                                val success =
                                    flashlightManager.setFlashlight(newState)

                                if(success){
                                    flashlightOn = newState
                                }
                            },

                            onBack = {

                                if(flashlightOn){
                                    flashlightManager.setFlashlight(false)
                                    flashlightOn = false
                                }

                                flashlightActive = false
                            }
                        )
                    }

                    else if(emergencyContactsActive){
                        EmergencyContactsScreen(
                            onBack = {
                                emergencyContactsActive = false
                            }
                        )
                    }
                    else {
                        SurviveAiHomeScreen(
                            onDisasterModeActivate = {
                                disasterModeActive = true
                            },

                            onSosClick = {
                                sosActive = true
                            },

                            onFlashlightClick = {
                                flashlightActive = true
                            }
                        )
                    }
                }
            }
        }
    }
}