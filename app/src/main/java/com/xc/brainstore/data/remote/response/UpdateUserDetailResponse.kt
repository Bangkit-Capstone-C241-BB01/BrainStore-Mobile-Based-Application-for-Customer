package com.xc.brainstore.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateUserDetailResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(
	@field:SerializedName("img_user")
	val userImg: String? = null,

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("user_email")
	val userEmail: String? = null,

	@field:SerializedName("user_phone")
	val userPhone: String? = null,

	@field:SerializedName("user_address")
	val userAddress: String? = null
)
