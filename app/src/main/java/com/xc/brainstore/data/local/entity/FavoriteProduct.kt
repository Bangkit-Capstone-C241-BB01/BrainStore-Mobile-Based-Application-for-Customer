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

    @ColumnInfo(name = "product_id")
    var id: Int? = null,

    @ColumnInfo(name = "product_name")
    var name: String? = null,

    @ColumnInfo(name = "product_image")
    var image: String? = null,

    @ColumnInfo(name = "product_price")
    var price: String? = null,

    @ColumnInfo(name = "product_rate")
    var rating: Int? = null
) : Parcelable
