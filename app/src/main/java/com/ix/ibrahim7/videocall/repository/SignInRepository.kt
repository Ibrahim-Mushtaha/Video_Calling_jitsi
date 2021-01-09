package com.ix.ibrahim7.videocall.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.util.Constant
import com.ix.ibrahim7.videocall.util.Constant.COLLECTION_USERS
import com.ix.ibrahim7.videocall.util.Constant.IS_SIGN_IN
import com.ix.ibrahim7.videocall.util.Constant.USER_DATA_PROFILE
import com.ix.ibrahim7.videocall.util.Constant.editor
import com.nurbk.ps.projectm.model.User

class SignInRepository private constructor(context: Context) {

    private val sigInLiveData = MutableLiveData<Boolean>()

    companion object {
        @Volatile
        private var instance: SignInRepository? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createRepository(context).also {
                    instance = it
                }
            }

        private fun createRepository(context: Context) =
            SignInRepository(context)

    }

    fun signIn(context: Context,email: String, password: String) =
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    editor(context).putBoolean(IS_SIGN_IN, true).apply()
                    getProfileData(context,FirebaseAuth.getInstance().currentUser!!.uid) {
                        sigInLiveData.postValue(true)
                    }
                } else {
                    sigInLiveData.postValue(false)
                }
            }

    fun getProfileData(context: Context, uid: String, onComplete: () -> Unit) =
        FirebaseFirestore.getInstance().collection(COLLECTION_USERS)
            .document(uid)
            .addSnapshotListener { querySnapshot, _ ->
                val userString = Gson().toJson(querySnapshot!!.toObject(User::class.java))
                editor(context)!!.putString(USER_DATA_PROFILE, userString).apply()
                onComplete()
            }

    fun getSignIn(): LiveData<Boolean> = sigInLiveData


}