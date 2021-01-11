package com.ix.ibrahim7.videocall.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.model.User


object Constant {

    const val SHARE = "Share"
    const val SIGNIN = "signIn"
    const val USERS = "User"
    const val USER_PROFILE = "userProfile"
    const val USER_DATA = "userData"
    const val TYPE_CALL = "typeCall"
    const val TYPE = "type"
    const val CALL_VIDEO = "video"
    const val CALL_AUDIO = "audio"
    const val VALUE_TYPE = "application/json"
    const val DATA = "data"
    const val REMOTE_MSG_TYPE = "type"
    const val REMOTE_MSG_INVITATION = "invitation"

    const val REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse"
    const val REMOTE_MSG_INVITATION_ACCEPTED = "accepted"
    const val REMOTE_MSG_INVITATION_REJECTED = "rejected"
    const val REMOTE_MSG_INVITATION_CANCEL = "cancel"
    const val FINISH_CALL = "finish_call"
    const val AUTH_VALUE = "AAAAqliMt44:APA91bH2YO0hmWyQ6AF5vpbl49kKoioFdUEL-wDq_ISm07BoEH7metKL-k_OySRWonBBub81oItgrN409862YvIKwmEdJtTXBEIFBWnM2psaOgSaKnm2fGwSYtbMgnxTdHRwXjFHcI2P"
    const val TOKEN = "token"
    const val BaseUrl = "https://fcm.googleapis.com/"
    const val MEETURL = "https://meet.jit.si"
    const val MEETING_ROOM = "meetingRoom"
    const val NOTIFICATION_DATE = "notificationData"
    const val CALL = "call"
    const val USER_STATUS = "status"

    lateinit var ringtone: Ringtone

    fun getSharePref(context: Context) =
        context.getSharedPreferences(SHARE, Activity.MODE_PRIVATE)

    fun editor(context: Context) = getSharePref(context).edit()


    lateinit var dialog: Dialog
    fun showDialog(activity: Activity) {
        dialog = Dialog(activity).apply {
            setContentView(R.layout.dialog_loading)
            setCancelable(true)
        }
        dialog.show()
    }

    fun getUserProfile(context: Context): User =
        Gson().fromJson(getSharePref(context)!!.getString(USER_PROFILE, ""), User::class.java)


    fun playrRingtone(context: Context){
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ringtone = RingtoneManager.getRingtone(context, notification)
        ringtone.play()
        ringtone.isLooping=true
    }

    fun stopRingtone(){
        ringtone.stop()
    }

}