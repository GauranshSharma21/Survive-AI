package com.example.surviveai.siren

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper

class SirenManager {

    private var toneGenerator: ToneGenerator? = null

    private val handler =
        Handler(Looper.getMainLooper())

    private var sirenRunning = false

    private val sirenRunnable = object : Runnable {

        override fun run() {

            if (!sirenRunning) {
                return
            }

            toneGenerator?.startTone(
                ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,
                500
            )

            handler.postDelayed(
                this,
                600
            )
        }
    }

    fun startSiren() {

        if (sirenRunning) {
            return
        }

        toneGenerator =
            ToneGenerator(
                AudioManager.STREAM_ALARM,
                ToneGenerator.MAX_VOLUME
            )

        sirenRunning = true

        handler.post(sirenRunnable)
    }

    fun stopSiren() {

        sirenRunning = false

        handler.removeCallbacks(
            sirenRunnable
        )

        toneGenerator?.stopTone() //If a ToneGenerator exists, stop its current tone.

        toneGenerator?.release() // tells Android that you're finished with the ToneGenerator and allows associated resources to be released.

        toneGenerator = null //There is currently no active ToneGenerator.
    }
}

