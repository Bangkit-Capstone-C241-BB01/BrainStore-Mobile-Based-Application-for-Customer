package com.xc.brainstore.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResponse(

	@field:SerializedName("total_results")
	val totalResults: Int? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)

@Parcelize
data class ProductsItem(

	@field:SerializedName("store_id")
	val storeId: Int? = null,

	@field:SerializedName("product_rate")
	val productRate: String? = null,

	@field:SerializedName("product_price")
	val productPrice: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("product_img")
	val productImg: String? = null,

	@field:SerializedName("product_desc")
	val productDesc: String? = null,

	@field:SerializedName("product_spec")
	val productSpec: String? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("img_quality")
	val imgQuality: String? = null,

	@field:SerializedName("store_name")
	val storeName: String? = null,

	@field:SerializedName("product_stock")
	val productStock: Int? = null,

	@field:SerializedName("product_category")
	val productCategory: String? = null,

	@field:SerializedName("store_location")
	val storeLocation: String? = null
) : Parcelable
