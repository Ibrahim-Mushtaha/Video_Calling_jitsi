package com.ix.ibrahim7.videocall.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class IncomingCallRepository constructor(context: Context) {

    val incomingCall = MutableLiveData<Boolean>()

    fun getIncomingCall(): LiveData<Boolean> = incomingCall
}