package com.ansorod.chat.data.remote

import com.google.gson.annotations.SerializedName

data class FCMBody(
    @SerializedName("to")
    val topic: String,

    @SerializedName("data")
    val data: MessageRemote
)