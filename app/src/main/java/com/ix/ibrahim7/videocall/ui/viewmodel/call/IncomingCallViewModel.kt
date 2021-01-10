package com.ix.ibrahim7.videocall.ui.viewmodel.call

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.videocall.repository.IncomingCallRepository

class IncomingCallViewModel(application: Application) : AndroidViewModel(application) {

    val incomingRepository = IncomingCallRepository(application.applicationContext)

    fun getCall() = incomingRepository.getIncomingCall();
}