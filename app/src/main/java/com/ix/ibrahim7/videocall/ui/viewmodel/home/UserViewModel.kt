package com.ix.ibrahim7.videocall.ui.viewmodel.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.videocall.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {


    private val UserRepository = UserRepository(application.applicationContext)

    val UserLiveData = UserRepository.getAllUserLiveData

    fun updateUserToken(context: Context) = UserRepository.updateUserToken(context)
    fun updateUserStatus(context: Context,status:Boolean) = UserRepository.updateUserStatus(context,status)

    fun getLogOut() = UserRepository.logOut()

    fun getProfile(context: Context,onComplete: () -> Unit) = UserRepository.signInRepository.getProfileData(
        context,FirebaseAuth.getInstance().currentUser!!.uid,
        onComplete
    )

    fun getAllUser() {
        GlobalScope.launch (Dispatchers.IO) {
            UserRepository.getAllUser()
        }
    }

}