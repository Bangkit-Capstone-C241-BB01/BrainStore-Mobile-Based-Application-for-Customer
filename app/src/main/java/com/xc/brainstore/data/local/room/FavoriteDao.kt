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
    suspend fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProduct)

    @Query("SELECT * from favorite_table ORDER BY product_id ASC")
    fun getAllFavorites(): LiveData<MutableList<FavoriteProduct>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_table LIMIT 1)")
    fun hasFavorites(): LiveData<Boolean>

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorite_table WHERE product_id = :idProduct")
    fun getFavoriteProductById(idProduct: Int): LiveData<FavoriteProduct?>
}