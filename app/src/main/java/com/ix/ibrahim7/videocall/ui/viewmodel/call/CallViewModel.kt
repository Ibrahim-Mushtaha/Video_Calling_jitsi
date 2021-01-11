package com.ix.ibrahim7.videocall.ui.viewmodel.call

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.videocall.model.NotificationData
import com.ix.ibrahim7.videocall.model.PushCalling
import com.ix.ibrahim7.videocall.repository.UserRepository
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_ACCEPTED
import com.ix.ibrahim7.videocall.util.Constant.REMOTE_MSG_INVITATION_CANCEL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class CallViewModel(application: Application) : AndroidViewModel(application) {

    val CallLivedata = MutableLiveData<Boolean>()

    fun startCalling(context: Context,meetingRoom:String,audio:Boolean){
        GlobalScope.launch {
            val options = JitsiMeetConferenceOptions.Builder()
                .setServerURL(URL(Constant.MEETURL))
                .setRoom(meetingRoom)
                .setWelcomePageEnabled(false)
                .setVideoMuted(audio)
                .build()
            JitsiMeetActivity.launch(context, options)
        }
    }

     fun sendRemoteMessage(context: Context,notificationData: NotificationData, type: String, messageTo:String){
         GlobalScope.launch (Dispatchers.IO) {
             PushCalling(
                 notificationData,
                 messageTo
             ).also {
                 PushCalling().Notification().sendMessage(context, it) { msg, done ->
                     if (done) {
                         try {
                             when(type){
                                 REMOTE_MSG_INVITATION_ACCEPTED,REMOTE_MSG_INVITATION,REMOTE_MSG_INVITATION_CANCEL-> CallLivedata.postValue(true)
                                 else-> CallLivedata.postValue(false)
                             }

                         } catch (e: Exception) {
                             e.printStackTrace()
                             CallLivedata.postValue(false)
                         }
                     } else {
                         CallLivedata.postValue(false)
                         Log.e("tttttttttttt", msg!!)
                     }
                 }
             }
         }
    }



}