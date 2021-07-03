package com.ansorod.chat.data.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RemoteApi {

    @POST("send")
    @Headers(
        "Authorization: key =$FCM_KEY",
        "Content-Type: application/json")
    suspend fun sendMessage(@Body messageData: FCMBody): ResponseBody

    companion object {
        const val BASE_URL = "https://fcm.googleapis.com/fcm/"
        const val FCM_KEY = ""
    }
}