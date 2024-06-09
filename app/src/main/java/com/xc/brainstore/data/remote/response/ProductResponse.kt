package com.xc.brainstore.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProductResponse(

    @field:SerializedName("ProductResponse")
    val productResponse: List<ProductResponseItem?>? = null
)

@Parcelize
data class ProductResponseItem(

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

    @field:SerializedName("product_stock")
    val productStock: Int? = null

) : Parcelable
