package com.xc.brainstore.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xc.brainstore.data.local.room.FavoriteDatabase
import com.xc.brainstore.data.repository.FavoriteRepository
import com.xc.brainstore.view.favorites.FavoriteViewModel

class ViewModelFactory2(private val repository: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory2? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory2 {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory2(provideRepository(context)).also { INSTANCE = it }
            }
        }

        private fun provideRepository(context: Context): FavoriteRepository {
            val database = FavoriteDatabase.getDatabase(context)
            return FavoriteRepository(database.favoriteDao())
        }
    }
}