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

    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "price")
    var price: String? = null,

    @ColumnInfo(name = "rating")
    var rating: Int
) : Parcelable
