package com.ix.ibrahim7.videocall.service

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.ui.activity.MainActivity
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.CALL
import com.ix.ibrahim7.videocall.util.Constant.CALL_AUDIO
import com.ix.ibrahim7.videocall.util.Constant.CALL_VIDEO
import com.ix.ibrahim7.videocall.util.Constant.MEETING_ROOM
import com.ix.ibrahim7.videocall.util.Constant.NOTIFICATION_DATE
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_RESPONSE
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


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationManager = NotificationManager(this)

        val dataJson =
            Gson().toJson(message.data)
        val data = Gson().fromJson(dataJson.toString(), NotificationData::class.java)

        Log.e("eee data Received",message.data.toString())
        /*startActivity(Intent(applicationContext,MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).apply {
            putExtra(CALL,1)
            putExtra(NOTIFICATION_DATE, data)
        })*/

        val intents = Intent(REMOTE_MSG_INVITATION_RESPONSE)
        intents.putExtra("data", data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intents)

    }

}