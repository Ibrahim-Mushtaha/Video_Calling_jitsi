package com.ix.ibrahim7.videocall.service

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.util.Constant.INVITATION_RESPONSE

class MassagingService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val dataJson =
            Gson().toJson(message.data)
        val data = Gson().fromJson(dataJson.toString(), NotificationData::class.java)

        val intents = Intent(INVITATION_RESPONSE)
        intents.putExtra("data", data)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intents)

    }

}