package com.ix.ibrahim7.videocall.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ix.ibrahim7.videocall.util.Constant.COLLECTION_USERS
import com.nurbk.ps.projectm.model.User


class SignUpRepository private constructor(context: Context) {

    private val sigUpLiveData = MutableLiveData<Boolean>()

    companion object {
        @Volatile
        private var instance: SignUpRepository? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createRepository(context).also {
                    instance = it
                }
            }

        private fun createRepository(context: Context) =
            SignUpRepository(context)

    }


    fun createNewAccount(user: User) =
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    sendEmailVerification(user)
                } else {
                    sigUpLiveData.postValue(false)
                }
            }

    fun getSignUp(): LiveData<Boolean> = sigUpLiveData

    private fun sendEmailVerification(user: User) {
        val userAuth = FirebaseAuth.getInstance().currentUser
        userAuth!!.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                insertUser(
                    User(
                        FirebaseAuth.getInstance().uid.toString(),
                        user.name,
                        user.email,
                        user.password,
                        user.image
                    )
                )
            }
        }
    }

    private fun insertUser(user: User) = FirebaseFirestore
        .getInstance()
        .collection(COLLECTION_USERS)
        .document(user.id).set(user)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                sigUpLiveData.postValue(true)
            } else {
                sigUpLiveData.postValue(false)
            }
        }
}