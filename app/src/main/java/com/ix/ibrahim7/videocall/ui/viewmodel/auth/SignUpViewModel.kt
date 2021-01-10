package com.ix.ibrahim7.videocall.ui.viewmodel.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.videocall.model.User
import com.ix.ibrahim7.videocall.repository.SignUpRepository


class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val signUpRepository = SignUpRepository()

    fun createAccount(user: User) = signUpRepository.createNewAccount(user)

    fun getSignUp() = signUpRepository.getSignUp()

}