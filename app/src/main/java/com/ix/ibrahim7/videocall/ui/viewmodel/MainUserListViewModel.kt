package com.ix.ibrahim7.videocall.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.videocall.repository.MainUserListRepository

class MainUserListViewModel(application: Application) : AndroidViewModel(application) {


    private val mainUserRepository = MainUserListRepository(application.applicationContext)

    val getAllUserLiveData = mainUserRepository.getAllUserLiveData

    fun updateUser(map: Map<String, String>, id: String, onComplete: () -> Unit) =
        mainUserRepository.updateData(map, id, onComplete)

    fun getUpdate() = mainUserRepository.getUpdateLiveData()

    fun getToken(context: Context,onComplete: () -> Unit) = mainUserRepository.getTokenId(context,onComplete)

    fun getLogOut() = mainUserRepository.logOut()

    fun getProfile(context: Context,onComplete: () -> Unit) = mainUserRepository.signInRepository.getProfileData(
        context,FirebaseAuth.getInstance().currentUser!!.uid,
        onComplete
    )


    init {
        mainUserRepository.getAllUser()
    }
}