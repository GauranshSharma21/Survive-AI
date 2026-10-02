package com.example.surviveai.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

class FlashlightManager(
    context : Context
){
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    private val cameraId: String? = cameraManager.cameraIdList.firstOrNull{ id->
        val characteristics = cameraManager.getCameraCharacteristics(id)

        characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
    }

    fun setFlashlight(enabled : Boolean): Boolean {
        val id = cameraId ?: return false

        return try{
            cameraManager.setTorchMode(id,enabled)
            true
        }  catch (_: Exception){
            false
        }
    }

}