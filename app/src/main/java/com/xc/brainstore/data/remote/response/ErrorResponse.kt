package com.xc.brainstore.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("msg")
    val message: String? = null
)
