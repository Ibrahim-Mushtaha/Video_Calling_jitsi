package com.nurbk.ps.projectm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class IncomingCallRepository private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: IncomingCallRepository? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createRepository(context).also {
                    instance = it
                }
            }

        private fun createRepository(context: Context) =
            IncomingCallRepository(context)
    }

    val incomingCall = MutableLiveData<Boolean>()

    fun getIncomingCall(): LiveData<Boolean> = incomingCall
}