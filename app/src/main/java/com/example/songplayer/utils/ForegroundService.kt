package com.example.songplayer.utils

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.songplayer.R

class ForegroundService:Service() {

    override fun onBind(p0: Intent?):IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setSmallIcon(R.drawable.upload_icon)
            .setContentTitle("Song is playing")
            .setContentText("song name here")
            .build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}