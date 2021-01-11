package com.ix.ibrahim7.videocall.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val msg =intent!!.getStringExtra("messege")


    }

}