package com.example.surviveai.sos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable

fun EmergencyContactsScreen(
    onBack: () -> Unit
){
    val context = LocalContext.current

    val contactStore = remember{
        EmergencyContactStore(context)
    }


    var name by remember{
        mutableStateOf("")
    }

    var phoneNumber by remember{
        mutableStateOf("")
    }

    var contacts by remember{
        mutableStateOf(contactStore.getContacts())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ){
        Text(
            text = "Emergency Contacts",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Add people who should receive your SOS messages.",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },

            label = {
                Text("Phone Number")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (name.isNotBlank() && phoneNumber.isNotBlank()) {
                    val newContact = EmergencyContact(
                        name = name,
                        phoneNumber = phoneNumber
                    )

                    val updatedContacts = contacts + newContact

                    contactStore.saveContacts(updatedContacts)
                    contacts = updatedContacts

                    name = ""
                    phoneNumber = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){
            Text("SAVE CONTACT")
        }
        Text(
            text = "Saved Contacts",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(contacts){ contact ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ){
                        Text(
                            text = contact.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = contact.phoneNumber,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Button(
                            onClick = {

                                contactStore.deleteContact(
                                    contact.phoneNumber
                                )

                                contacts =
                                    contactStore.getContacts()
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ){
                            Text("REMOVE")
                        }
                    }
                }
            }
        }
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("BACK")
        }

    }
}




