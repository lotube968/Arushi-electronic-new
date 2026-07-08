package com.example.data

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.IBinder
import android.util.Log

class AlertSoundService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == "STOP") {
            stopSound()
            stopSelf()
        } else {
            playSound()
        }
        return START_STICKY
    }

    private fun playSound() {
        if (mediaPlayer == null) {
            try {
                val alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                    ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(this@AlertSoundService, alertUri)
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build()
                    )
                    isLooping = true
                    prepare()
                    start()
                }
                Log.d("AlertSoundService", "Continuous alert sound started in background service")
            } catch (e: Exception) {
                Log.e("AlertSoundService", "Failed to start sound in service: ${e.message}")
            }
        }
    }

    private fun stopSound() {
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
            } catch (e: Exception) {
                Log.e("AlertSoundService", "Error stopping sound in service", e)
            }
        }
        mediaPlayer = null
        Log.d("AlertSoundService", "Continuous alert sound stopped in background service")
    }

    override fun onDestroy() {
        stopSound()
        super.onDestroy()
    }
}
