package com.xc.brainstore.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailRequest(
    @SerializedName("user_name")
    val userName: String? = null,

    @SerializedName("user_address")
    val userAddress: String? = null,

    @SerializedName("user_phone")
    val userPhone: String? = null,

    @SerializedName("user_img")
    val userImg: String? = null
)
