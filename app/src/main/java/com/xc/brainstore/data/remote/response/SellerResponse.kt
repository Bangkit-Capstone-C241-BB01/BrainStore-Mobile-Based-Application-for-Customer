package com.xc.brainstore.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerResponse(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("store_name")
	val storeName: String? = null,

	@field:SerializedName("store_img")
	val storeImg: String? = null,

	@field:SerializedName("store_location")
	val storeLocation: String? = null
) : Parcelable
