package com.ix.ibrahim7.videocall.BroadCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val msg =intent!!.getStringExtra("messege")


    }

}