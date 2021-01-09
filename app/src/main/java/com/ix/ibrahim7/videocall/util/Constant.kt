package com.ix.ibrahim7.videocall.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.R
import com.nurbk.ps.projectm.model.User

object Constant {

    const val NAME_FILE_PREF = "PreferencesManagerProject"

    const val IS_SIGN_IN = "signIn"
    const val COLLECTION_USERS = "User"

    const val USER_DATA_PROFILE = "userProfile"

    const val USER_DATA = "userData"
    const val TYPE_CALL = "typeCall"

    const val CALL_VIDEO = "video"
    const val CALL_AUDIO = "audio"

    const val REMOTE_MSG_AUTHORIZATION = "Authorization"
    const val AUTH_VALUE =
        "AAAAqliMt44:APA91bH2YO0hmWyQ6AF5vpbl49kKoioFdUEL-wDq_ISm07BoEH7metKL-k_OySRWonBBub81oItgrN409862YvIKwmEdJtTXBEIFBWnM2psaOgSaKnm2fGwSYtbMgnxTdHRwXjFHcI2P"

    const val REMOTE_MSG_CONTENT_TYPE = "Content-Type"
    const val VALUE_TYPE = "application/json"


    const val REMOTE_MSG_TYPE = "type"
    const val REMOTE_MSG_INVITATION = "invitation"


    const val REMOTE_MSG_MEETING_TYPE = "meetingType"
    const val REMOTE_MSG_INVITER_TOKEN = "inviterToken"
    const val REMOTE_MSG_DATA = "data"
    const val REMOTE_MSG_REGISTRATION_IDS = "registration_ids"

    const val REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse"
    const val REMOTE_MSG_INVITATION_ACCEPTED = "accepted"
    const val REMOTE_MSG_INVITATION_REJECTED = "rejected"
    const val REMOTE_MSG_INVITATION_CANCEL = "cancel"

    fun mapRemoteHeaders() = HashMap<String, String>().apply {
        put(REMOTE_MSG_AUTHORIZATION, AUTH_VALUE)
        put(REMOTE_MSG_CONTENT_TYPE, VALUE_TYPE)
    }

    fun getSharePref(context: Context) =
        context.getSharedPreferences("Share", Activity.MODE_PRIVATE)

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
        Gson().fromJson(getSharePref(context)!!.getString(USER_DATA_PROFILE, ""), User::class.java)

}