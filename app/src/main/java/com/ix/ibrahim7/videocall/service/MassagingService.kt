package com.ix.ibrahim7.videocall.service

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ix.ibrahim7.videocall.ui.activity.MainActivity
import com.ix.ibrahim7.videocall.util.Constant.CALL
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.MEETING_ROOM
import com.ix.ibrahim7.videocall.util.Constant.TYPE
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

        startActivity(Intent(applicationContext,MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply {
            putExtra(CALL,1)
            putExtra(TYPE,if (p0.data[TYPE] == CALL_VIDEO) true else false)
            putExtra(MEETING_ROOM,p0.data[MEETING_ROOM])
        })
        Log.e("eee data Received",p0.data.toString())

    }

}