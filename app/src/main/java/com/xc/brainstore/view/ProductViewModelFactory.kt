package com.xc.brainstore.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xc.brainstore.data.repository.ProductRepository
import com.xc.brainstore.di.Injection
import com.xc.brainstore.view.detail.DetailProductViewModel
import com.xc.brainstore.view.home.HomeViewModel

class ProductViewModelFactory(private val repository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DetailProductViewModel::class.java) -> {
                DetailProductViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ProductViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ProductViewModelFactory::class.java) {
                    INSTANCE = ProductViewModelFactory(
                        Injection.provideProductRepository(context)
                    )
                }
            }
            return INSTANCE as ProductViewModelFactory
        }
    }
}