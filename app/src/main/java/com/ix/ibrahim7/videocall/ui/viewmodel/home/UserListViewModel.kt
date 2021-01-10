package com.ix.ibrahim7.videocall.ui.viewmodel.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.videocall.repository.UserListRepository

class UserListViewModel(application: Application) : AndroidViewModel(application) {


    private val UserRepository = UserListRepository(application.applicationContext)

    val UserLiveData = UserRepository.getAllUserLiveData

    /*fun updateUser(map: Map<String, String>, id: String, onComplete: () -> Unit) =
        mainUserRepository.updateData(map, id, onComplete)

    fun getUpdate() = mainUserRepository.getUpdateLiveData()*/

    fun updateUserToken(context: Context, onComplete: () -> Unit) = UserRepository.updateUserToken(context,onComplete)

    fun getLogOut() = UserRepository.logOut()

    fun getProfile(context: Context,onComplete: () -> Unit) = UserRepository.signInRepository.getProfileData(
        context,FirebaseAuth.getInstance().currentUser!!.uid,
        onComplete
    )


    init {
        UserRepository.getAllUser()
    }
}