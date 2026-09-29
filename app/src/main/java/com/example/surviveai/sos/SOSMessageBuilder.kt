package com.example.surviveai.sos

import android.location.Location

object SOSMessageBuilder{

    fun buildMessage(location : Location) : String{
        val latitude = location.latitude
        val longitude = location.longitude

        val locationLink = "https://maps.google.com/?q=$latitude,$longitude"

        return """
             🚨 EMERGENCY SOS
             
             I need help.Please rescue me or send assistance.
             
             My current location: $locationLink
             
             Sent from Survive AI
         """.trimIndent()
    }
}