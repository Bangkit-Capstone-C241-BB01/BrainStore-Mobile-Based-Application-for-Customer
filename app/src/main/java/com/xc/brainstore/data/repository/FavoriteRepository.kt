package com.xc.brainstore.data.repository

import androidx.lifecycle.LiveData
import com.xc.brainstore.data.local.entity.FavoriteProduct
import com.xc.brainstore.data.local.room.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    private val mScope = CoroutineScope(Dispatchers.IO)

    fun getAllFavorites(): LiveData<MutableList<FavoriteProduct>> = favoriteDao.getAllFavorites()

    fun insert(favoriteProduct: FavoriteProduct) {
        mScope.launch {
            withContext(Dispatchers.IO) {
                favoriteDao.insert(favoriteProduct)
            }
        }
    }

    fun delete(favoriteProduct: FavoriteProduct) {
        mScope.launch {
            withContext(Dispatchers.IO) {
                favoriteDao.delete(favoriteProduct)
            }
        }
    }

    fun deleteAll() {
        mScope.launch {
            withContext(Dispatchers.IO) {
                favoriteDao.deleteAll()
            }
        }
    }

    fun getFavoriteProductById(id: Int): LiveData<FavoriteProduct?> {
        return favoriteDao.getFavoriteProductById(id)
    }
}