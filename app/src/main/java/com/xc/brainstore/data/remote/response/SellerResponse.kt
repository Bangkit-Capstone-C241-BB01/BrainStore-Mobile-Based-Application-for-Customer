package com.xc.brainstore.data.remote.response

import com.google.gson.annotations.SerializedName

data class SellerResponse(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("store_desc")
	val storeDesc: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Any? = null,

	@field:SerializedName("user_name")
	val userName: Any? = null,

	@field:SerializedName("store_name")
	val storeName: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("store_img")
	val storeImg: String? = null,

	@field:SerializedName("store_rate")
	val storeRate: String? = null,

	@field:SerializedName("store_location")
	val storeLocation: String? = null
)
