package com.example.lesson_10_safarov

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundedService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("Bounded service", "onCreate")
    }

    override fun onBind(p0: Intent?): IBinder {
        Log.i("Bounded service", "onBind")
        return Binder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("Bounded service", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Bounded service", "onDestroy")
    }
}
