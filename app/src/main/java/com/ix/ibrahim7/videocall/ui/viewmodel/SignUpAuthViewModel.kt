package com.nurbk.ps.projectm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.projectm.model.User
import com.ix.ibrahim7.videocall.repository.SignUpRepository


class SignUpAuthViewModel(application: Application) : AndroidViewModel(application) {

    private val signUpRepository = SignUpRepository(application.applicationContext)


    fun createAccount(user: User) = signUpRepository.createNewAccount(user)
    fun getSignUp() = signUpRepository.getSignUp()
}