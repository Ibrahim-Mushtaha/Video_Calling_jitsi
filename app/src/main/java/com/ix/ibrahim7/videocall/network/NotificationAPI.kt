package com.ix.ibrahim7.videocall.network

import com.ix.ibrahim7.videocall.util.Constant.AUTH_VALUE
import com.ix.ibrahim7.videocall.util.Constant.VALUE_TYPE
import com.ix.ibrahim7.videocall.model.PushCalling
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {

    @Headers("Authorization:key=$AUTH_VALUE","Content-Type:$VALUE_TYPE")
    @POST("fcm/send")
    suspend fun sendRemoteMessage(
        @Body notification: PushCalling
    ):Response<ResponseBody>

}