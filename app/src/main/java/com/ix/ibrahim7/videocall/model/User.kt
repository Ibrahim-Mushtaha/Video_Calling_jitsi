package com.ix.ibrahim7.videocall.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val image: String = "",
    val token: String = "",
    val status: Boolean =false
) : Parcelable