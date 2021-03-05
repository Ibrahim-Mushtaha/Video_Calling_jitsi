package com.ix.ibrahim7.videocall.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NotificationData(
    val receiverToken: String = "",
    val type: String = "",
    val senderToken: String = "",
    val data: String = "",
    val name: String = "",
    val email: String = "",
    val meetingRoom: String = "",
    val meetingType: String = "",
    val acceptedOrRejected: Boolean = false
) : Parcelable

