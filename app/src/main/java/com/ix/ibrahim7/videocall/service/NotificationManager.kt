package com.ix.ibrahim7.videocall.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ix.ibrahim7.videocall.R

class NotificationManager(var context: Context) {
    val NOTIFICATION_ID = 1000
    val CHANNEL_ID = "2000"

    fun showNotification(id: Int, title: String, message: String,messageID:String?,channelID:String?, intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val nBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        val notification = nBuilder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setColor(Color.BLUE)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT).build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "first channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(true)
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(id, notification)
    }

    fun getNotificationId(): Int {
        return NOTIFICATION_ID
    }


}