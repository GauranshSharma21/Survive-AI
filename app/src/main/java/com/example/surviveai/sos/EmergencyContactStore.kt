package com.example.surviveai.sos

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class EmergencyContactStore(
    context: Context
){
    private val preferences =
        context.getSharedPreferences(
            "emergency_contacts",
            Context.MODE_PRIVATE
        )


    fun saveContacts(contacts : List<EmergencyContact>){
        val jsonArray = JSONArray()

        for(contact in contacts){
            val jsonObject = JSONObject()

            jsonObject.put("name",contact.name)
            jsonObject.put("phoneNumber",contact.phoneNumber)

            jsonArray.put(jsonObject)
        }

        preferences.edit()
            .putString("contacts",jsonArray.toString())
            .apply()
    }

    fun getContacts(): List<EmergencyContact> {
        val contacts = mutableListOf<EmergencyContact>()

        val savedContacts =
            preferences.getString("contacts",null)
                ?: return contacts

        val jsonArray = JSONArray(savedContacts)

        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)

            val name = jsonObject.getString("name")

            val phoneNumber = jsonObject.getString("phoneNumber")

            contacts.add(
                EmergencyContact(
                    name = name,
                    phoneNumber = phoneNumber
                )
            )
        }

        return contacts
    }

    fun deleteContact(phoneNumber: String) {

        val contacts = getContacts()

        val updatedContacts = contacts.filter {
            it.phoneNumber != phoneNumber
        }

        saveContacts(updatedContacts)
    }
}

