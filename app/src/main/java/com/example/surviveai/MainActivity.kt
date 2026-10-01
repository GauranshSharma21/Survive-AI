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
import com.example.surviveai.sos.SOSScreen
import com.example.surviveai.sos.EmergencyContactsScreen
import com.example.surviveai.sos.SOSLocationManager
import com.example.surviveai.sos.SOSMessageBuilder
import com.example.surviveai.sos.EmergencyContactStore
import com.example.surviveai.sos.SOSMessageSender

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
                                sosLocationMessage = "Getting current location...."

                                sosLocationManager.getCurrentLocation(
                                    onLocationReceived = { location ->

                                        if(location != null){
                                            val message = SOSMessageBuilder.buildMessage(location)

                                            sosLocationMessage = message

                                            val contacts = emergencyContactStore.getContacts()

                                            if(contacts.isEmpty()){
                                                sosLocationMessage = "No emergency contact saved"
                                            }else{
                                                //for now, we are using the first saved contact
                                                val firstContact = contacts.first()

                                                //opening the SMS app with the number and message

                                                sosMessageSender.openSmsApp(
                                                    phoneNumber = firstContact.phoneNumber,
                                                    message = message,
                                                    onSmsAppNotFound = {
                                                        sosLocationMessage = "No sms found on this device"
                                                    }
                                                )
                                            }
                                        } else{
                                            sosLocationMessage = "Unable to get location"
                                        }

                                    },
                                    onError = {
                                        sosLocationMessage = "Location permission is required"
                                    }
                                )
                            },
                            onManageContacts = {
                                sosActive = false
                                emergencyContactsActive = true
                            },
                            locationMessage = sosLocationMessage
                        )
                    }

                    else if(emergencyContactsActive){
                        EmergencyContactsScreen(
                            onBack = {
                                emergencyContactsActive = false
                            }
                        )
                    }
                    else{
                        SurviveAiHomeScreen(
                            onDisasterModeActivate = {
                                disasterModeActive = true
                            },
                            onSosClick = {
                                sosActive = true
                            }
                        )
                    }
                }
            }
        }
    }
}