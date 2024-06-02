package com.xc.brainstore.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
