package com.ix.ibrahim7.videocall.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.ix.ibrahim7.videocall.util.Constant.USERS
import com.ix.ibrahim7.videocall.util.Constant.SIGNIN
import com.ix.ibrahim7.videocall.util.Constant.USER_PROFILE
import com.ix.ibrahim7.videocall.util.Constant.editor
import com.ix.ibrahim7.videocall.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInRepository{

    private val sigInLiveData = MutableLiveData<Boolean>()
    fun getSignIn(): LiveData<Boolean> = sigInLiveData


    fun signIn(context: Context,email: String, password: String) =
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    editor(context).putBoolean(SIGNIN, true).apply()
                    getProfileData(context,FirebaseAuth.getInstance().currentUser!!.uid) {
                        sigInLiveData.postValue(true)
                    }
                } else {
                    sigInLiveData.postValue(false)
                }
            }

    fun getProfileData(context: Context, uid: String, onComplete: () -> Unit) {
        GlobalScope.launch (Dispatchers.IO) {
            FirebaseFirestore.getInstance().collection(USERS)
                .document(uid)
                .addSnapshotListener { querySnapshot, _ ->
                    val userString = Gson().toJson(querySnapshot!!.toObject(User::class.java))
                    editor(context)!!.putString(USER_PROFILE, userString).apply()
                }
            delay(1500)
            onComplete()
        }
    }



}