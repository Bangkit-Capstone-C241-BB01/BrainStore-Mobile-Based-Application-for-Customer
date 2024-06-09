package com.xc.brainstore.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize
data class FavoriteProduct(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo(name = "store_id")
    var storeId: Int? = null,

    @ColumnInfo(name = "product_id")
    var id: Int? = null,

    @ColumnInfo(name = "product_img")
    var productImg: String? = null,

    @ColumnInfo(name = "product_name")
    var productName: String? = null,

    @ColumnInfo(name = "product_price")
    var productPrice: String? = null,

    @ColumnInfo(name = "product_rate")
    var productRate: String? = null,

    @ColumnInfo(name = "product_stock")
    var productStock: Int? = null,

    @ColumnInfo(name = "product_spec")
    var productSpec: String? = null,

    @ColumnInfo(name = "product_desc")
    var productDesc: String? = null

) : Parcelable
