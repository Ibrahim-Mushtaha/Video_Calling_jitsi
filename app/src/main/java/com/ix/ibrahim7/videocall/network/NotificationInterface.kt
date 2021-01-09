package com.ix.ibrahim7.videocall.network

import com.ix.ibrahim7.videocall.util.Constant.AUTH_VALUE
import com.ix.ibrahim7.videocall.util.Constant.VALUE_TYPE
import com.nurbk.ps.projectm.model.PushCalling
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationInterface {

    @Headers(
        "Authorization: key=${AUTH_VALUE}",
        "Content-Type:${VALUE_TYPE}"
    )
    @POST("send")
    fun sendRemoteMessage(
        @Body calling: PushCalling
    ): Call<ResponseBody>

}