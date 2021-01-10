package com.ix.ibrahim7.videocall.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotificationData(
    val type: String = "",
    val invitation: String = "",
    val meetingType: String = "",
    val senderToken: String = "",
    val receiverToken: String = "",
    val data: String = "",
    val registration_ids: String = "",
    val name: String = "",
    val email: String = "",
    val meetingRoom: String = "",
    val acceptedOrRejected: Boolean = false
) : Parcelable

