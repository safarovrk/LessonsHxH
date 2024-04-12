package com.example.lesson_10_safarov

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log


class StartedBoundedService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i("Started&Bounded service", "onCreate")
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.i("Started&Bounded service", "onBind")
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Started&Bounded service", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("Started&Bounded service", "onRebind")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("Started&Bounded service", "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Started&Bounded service", "onDestroy")
    }

}