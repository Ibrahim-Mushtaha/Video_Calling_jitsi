package com.ix.ibrahim7.videocall.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.ix.ibrahim7.videocall.util.Constant.COLLECTION_USERS
import com.ix.ibrahim7.videocall.util.Constant.getUserProfile
import com.nurbk.ps.projectm.model.User

class MainUserListRepository private constructor(val context: Context) {

    private val updateLiveData = MutableLiveData<Boolean>()
    val signInRepository = SignInRepository(context)

    private val _getAllUserLiveData = MutableLiveData<List<User>>()
    val getAllUserLiveData: LiveData<List<User>> = _getAllUserLiveData


    companion object {
        @Volatile
        private var instance: MainUserListRepository? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createRepository(context).also {
                    instance = it
                }
            }

        private fun createRepository(context: Context) =
            MainUserListRepository(context)
    }


    fun updateData(data: Map<String, Any>, id: String, onComplete: () -> Unit) = FirebaseFirestore
        .getInstance()
        .collection(COLLECTION_USERS)
        .document(id)
        .update(
            data
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                updateLiveData.postValue(true)
                onComplete()
            } else {
                updateLiveData.postValue(false)
            }
        }


    fun getUpdateLiveData(): LiveData<Boolean> = updateLiveData


    fun getTokenId(context: Context,onComplete: () -> Unit) {
        val user = getUserProfile(context)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                updateData(mapOf("token" to it.result.toString()), user.id) {
                    signInRepository.getProfileData(context,user.id) {
                        onComplete()
                    }
                }
            }

        }
    }

    fun logOut() {
        val user =
            getUserProfile(context)
        updateData(mapOf("token" to " "), user.id) {
            FirebaseAuth.getInstance().signOut()
        }
    }


    fun getAllUser() {
        val array = ArrayList<User>()
        FirebaseFirestore
            .getInstance()
            .collection(COLLECTION_USERS)
            .addSnapshotListener { querySnapshot, _ ->
                array.clear()
                querySnapshot?.documents!!.forEach {
                    val item = it.toObject(User::class.java)

                    if (item!!.id != FirebaseAuth.getInstance().currentUser!!.uid)
                        array.add(item)
                }
                _getAllUserLiveData.value = array
            }


    }
}