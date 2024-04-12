package com.example.lesson_3_safarov.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.lesson_3_safarov.R
import com.example.lesson_3_safarov.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Calendar

private const val PUSH_KEY_TITLE = "titleqwe"
private const val PUSH_KEY_BODY = "bodyasd"

class LessonFirebaseMessagingService : FirebaseMessagingService() {
    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onNewToken(pushToken: String) {
        super.onNewToken(pushToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val appName = getString(R.string.app_name)

        var title = remoteMessage.data[PUSH_KEY_TITLE]
        if (title.isNullOrBlank()) {
            title = remoteMessage.notification?.title ?: appName
        }

        var body = remoteMessage.data[PUSH_KEY_BODY]
        if (body.isNullOrBlank()) {
            body =  remoteMessage.notification?.body ?: ""
        }

        var intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        if (intent == null) {
            intent = MainActivity.createStartIntent(this)
        }
        remoteMessage.data.keys.forEach { intent.putExtra(it, remoteMessage.data[it]) }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> NotificationCompat.Builder(this, appName)
            else -> NotificationCompat.Builder(this)
        }

        notificationBuilder
            .setStyle(NotificationCompat.BigTextStyle(notificationBuilder).bigText(body))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(Calendar.getInstance().timeInMillis.toInt(), notificationBuilder.build())
    }
}