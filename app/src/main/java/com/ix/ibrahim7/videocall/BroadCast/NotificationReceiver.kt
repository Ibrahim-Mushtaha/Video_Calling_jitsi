package com.ix.ibrahim7.videocall.BroadCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val msg =intent!!.getStringExtra("messege")
        val channelId =intent.getStringExtra("channelID")

        val hashMap = HashMap<String,Any>()
        hashMap["status"] = "1"

        Toast.makeText(context,msg+"|| chaneelID:${channelId}",Toast.LENGTH_SHORT).show()
    }

}