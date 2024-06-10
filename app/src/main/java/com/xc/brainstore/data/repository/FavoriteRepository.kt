package com.xc.brainstore.data.repository

import androidx.lifecycle.LiveData
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getAllFavorites(): LiveData<MutableList<FavoriteProduct>> = favoriteDao.getAllFavorites()

    suspend fun insert(favoriteProduct: FavoriteProduct) {
        favoriteDao.insert(favoriteProduct)
    }

    suspend fun delete(favoriteProduct: FavoriteProduct) {
        favoriteDao.delete(favoriteProduct)
    }

    suspend fun deleteAll() {
        favoriteDao.deleteAll()
    }

    fun getFavoriteProductById(id: Int): LiveData<FavoriteProduct?> {
        return favoriteDao.getFavoriteProductById(id)
    }
}