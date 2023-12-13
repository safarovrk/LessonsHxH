package com.example.lesson_10_safarov

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    // Нейминг...
    private var isBoundedServiceBound = false
    private val boundedConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            isBoundedServiceBound = true
            Log.i("Bounded connect", "onServiceConnected")
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isBoundedServiceBound = false
            Log.i("Bounded connect", "onServiceDisconnected")
        }
    }

    private var isStartedBoundedServiceBound = false
    private val startedBoundedConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            isStartedBoundedServiceBound = true
            Log.i("Started&Bounded connect", "onServiceConnected")
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isStartedBoundedServiceBound = false
            Log.i("Started&Bounded connect", "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val startedServiceIntent = Intent(this@MainActivity, StartedService::class.java)
            startService(startedServiceIntent)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val boundedServiceIntent = Intent(this@MainActivity, BoundedService::class.java)
            bindService(boundedServiceIntent, boundedConnection, BIND_AUTO_CREATE)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val boundedStartedServiceIntent = Intent(this@MainActivity, StartedBoundedService::class.java)
            startService(boundedStartedServiceIntent)
            bindService(boundedStartedServiceIntent, startedBoundedConnection, BIND_AUTO_CREATE)
        }

        // При запуске из Main потока соответствующие коллбэки вызываются последовательно
        /*val startedServiceIntent = Intent(this, StartedService::class.java)
        startService(startedServiceIntent)

        val boundedServiceIntent = Intent(this, BoundedService::class.java)
        bindService(boundedServiceIntent, boundedConnection, BIND_AUTO_CREATE)

        val boundedStartedServiceIntent = Intent(this, StartedBoundedService::class.java)
        startService(boundedStartedServiceIntent)
        bindService(boundedStartedServiceIntent, startedBoundedConnection, BIND_AUTO_CREATE)*/
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("isBoundedServiceBound", isBoundedServiceBound.toString())
        if (isBoundedServiceBound) {
            unbindService(boundedConnection)
            isBoundedServiceBound = false
        }
        Log.e("isStartedBoundedService", isStartedBoundedServiceBound.toString())
        if (isStartedBoundedServiceBound) {
            unbindService(startedBoundedConnection)
            isStartedBoundedServiceBound = false
        }
    }
}