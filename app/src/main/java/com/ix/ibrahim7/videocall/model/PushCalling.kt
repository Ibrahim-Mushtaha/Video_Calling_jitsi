package com.ix.ibrahim7.videocall.model

import android.content.Context
import com.ix.ibrahim7.videocall.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class PushCalling(
    val data: NotificationData? = NotificationData(),
    val to: String
){
    constructor():this(null,"")
    inner class Notification{
        fun sendNotification(context: Context,notification: PushCalling,onComplete: (msg:String?,done:Boolean) -> Unit) = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance(context).notificationApi.sendRemoteMessage(notification)
                if(response.isSuccessful) {
                    Timber.e("eee send Response: Notification sended Successfuly")
                    onComplete(response.body().toString(),true)
                } else {
                    Timber.e("eee send Response: Notification ${response.errorBody().toString()}")
                    onComplete(response.errorBody().toString(),false)
                }
            } catch(e: Exception) {
                onComplete(e.message.toString(),false)
                Timber.e("eee send Response: Notification ${e.message.toString()}")
            }
        }

    }
}