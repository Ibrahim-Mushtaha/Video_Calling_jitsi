package com.ix.ibrahim7.videocall.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.ix.ibrahim7.videocall.repository.SignInRepository

class SignInAuthViewModel(application: Application) : AndroidViewModel(application) {


    private val sigInRepository = SignInRepository(application.applicationContext)


    fun signInWithEmailAndPassword(context: Context,email: String, password: String) =
        sigInRepository.signIn(context,email = email, password = password)

    fun getSignIn() = sigInRepository.getSignIn()


}