package com.ix.ibrahim7.videocall.service

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class MassagingService : FirebaseMessagingService() {


    lateinit var notificationManager: NotificationManager
    override fun onNewToken(p0: String) {
        Log.e("eee token", p0)
        super.onNewToken(p0)
    }


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        notificationManager = NotificationManager(this)


        val options =
            JitsiMeetConferenceOptions.Builder()
                .setServerURL(URL("https://meet.jit.si"))
                .setRoom(p0.data["meetingRoom"])
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setAudioOnly(false)
                .setWelcomePageEnabled(false)
                .build()
        JitsiMeetActivity.launch(applicationContext,options)
        Log.e("eee data Received",p0.data.toString())



    }

}