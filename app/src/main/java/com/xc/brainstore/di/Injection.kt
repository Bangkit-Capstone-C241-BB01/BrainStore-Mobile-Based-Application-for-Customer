package com.xc.brainstore.di

import android.content.Context
import com.xc.brainstore.data.local.pref.UserPreference
import com.xc.brainstore.data.local.pref.dataStore
import com.xc.brainstore.data.repository.ProductRepository
import com.xc.brainstore.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideProductRepository(context: Context): ProductRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ProductRepository.getInstance(pref)
    }
}