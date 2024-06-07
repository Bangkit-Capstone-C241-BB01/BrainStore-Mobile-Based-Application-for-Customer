package com.xc.brainstore.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.ProductsItem
import com.xc.brainstore.data.repository.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductRepository) : ViewModel() {

    val productList: LiveData<List<ProductResponseItem>> = repository.productList
    val productData: LiveData<List<ProductsItem?>?> = repository.productData
    val isLoading: LiveData<Boolean> = repository.isLoading
    val message: LiveData<String?> get() = repository.message

    fun getProduct() {
        viewModelScope.launch {
            repository.getProduct()
        }
    }

    fun getNewestProduct() {
        viewModelScope.launch {
            repository.getNewestProduct()
        }
    }

    fun getPopularProduct() {
        viewModelScope.launch {
            repository.getPopularProduct()
        }
    }

    fun getLocationProduct() {
        viewModelScope.launch {
            repository.getLocationProduct()
        }
    }
    fun searchProduct(productName: String) {
        viewModelScope.launch {
            repository.searchProduct(productName)
        }
    }

    fun clearSearch() {
        repository.clearSearch()
    }
}