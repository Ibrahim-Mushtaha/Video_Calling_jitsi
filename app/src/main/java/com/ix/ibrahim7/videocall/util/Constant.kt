package com.ix.ibrahim7.videocall.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.R
import com.ix.ibrahim7.videocall.model.User

object Constant {

    const val SHARE = "Share"
    const val SIGNIN = "signIn"
    const val USERS_COLLECTION = "User"
    const val USER_PROFILE = "userProfile"
    const val USER_DATA = "userData"
    const val TYPE_CALL = "typeCall"
    const val CALL_VIDEO = "video"
    const val CALL_AUDIO = "audio"
    const val VALUE_TYPE = "application/json"
    const val DATA = "data"
    const val REMOTE_MSG_INVITATION = "invitation"
    const val REMOTE_INVITATION_RESPONSE = "invitationResponse"
    const val REMOTE_INVITATION_ACCEPTED = "accepted"
    const val REMOTE_INVITATION_REJECTED = "rejected"
    const val REMOTE_INVITATION_CANCEL = "cancel"


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

}