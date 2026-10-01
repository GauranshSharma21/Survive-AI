package com.example.surviveai.sos

import android.content.Context
import android.content.Intent
import android.net.Uri

class SOSMessageSender(
    private val context: Context
){
    fun openSmsApp(
        phoneNumber: String,
        message: String,
        onSmsAppNotFound: () -> Unit
    ){
        val intent = Intent(Intent.ACTION_SENDTO).apply{
            data = Uri.parse("smsto:$phoneNumber")

            putExtra(
                "sms_body",
                message
            )
        }

        if(intent.resolveActivity(context.packageManager) != null){
            context.startActivity(intent)
        }
        else{
            onSmsAppNotFound()
        }
    }
}