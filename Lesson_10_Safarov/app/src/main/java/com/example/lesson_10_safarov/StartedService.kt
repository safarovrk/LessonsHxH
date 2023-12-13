package com.example.lesson_10_safarov

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log


class StartedService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("Started service", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Started service", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Started service", "onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? { return null }

}