package com.ix.ibrahim7.videocall.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: ApiClient? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createPreferences(context).also {
                    instance = it
                }
            }

        private fun createPreferences(context: Context) =
            ApiClient(context)

    }

    private val BASE_URL = "https://fcm.googleapis.com/fcm/"
    private var retrofit: Retrofit
    var notificationInterface: NotificationInterface

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        notificationInterface = retrofit.create(NotificationInterface::class.java)

    }

}