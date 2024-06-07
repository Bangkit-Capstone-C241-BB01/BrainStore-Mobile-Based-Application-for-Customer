package com.xc.brainstore.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xc.brainstore.data.local.entity.FavoriteProduct

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    fun delete(favoriteProduct: FavoriteProduct)

    @Query("SELECT * from favorite_table ORDER BY product_id ASC")
    fun getAllFavorites(): LiveData<MutableList<FavoriteProduct>>

    @Query("SELECT * FROM favorite_table WHERE product_id = :idProduct")
    fun getFavoriteProductById(idProduct: Int): LiveData<FavoriteProduct?>
}