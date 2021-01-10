package com.ix.ibrahim7.videocall.network

import android.content.Context
import com.ix.ibrahim7.videocall.util.Constant.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance private constructor(context: Context) {

    private var retrofit: Retrofit
    lateinit var notificationApi: NotificationAPI

    companion object {
        @Volatile
        private var instance: RetrofitInstance? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                instance ?: createPreferences(context).also {
                    instance = it
                }
            }

        private fun createPreferences(context: Context) =
            RetrofitInstance(context)

    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        notificationApi = retrofit.create(NotificationAPI::class.java)

    }

}