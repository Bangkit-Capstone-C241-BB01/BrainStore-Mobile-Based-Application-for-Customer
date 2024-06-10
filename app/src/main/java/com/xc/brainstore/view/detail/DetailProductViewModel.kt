package com.xc.brainstore.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DetailProductViewModel(private val repository: ProductRepository) : ViewModel() {

    val productDetail: LiveData<ProductResponseItem?> = repository.productDetail
    val isLoading: LiveData<Boolean> = repository.isLoading
    val storeDetail: LiveData<SellerResponse?> = repository.storeDetail
    val message: LiveData<String?> get() = repository.message

    fun getProductDetail(id: Int?) {
        viewModelScope.launch {
            repository.getProductDetail(id)
        }
    }

    fun getStoreDetail(id: Int?) {
        viewModelScope.launch {
            repository.getStore(id)
        }
    }

    fun clearProductDetail() {
        viewModelScope.launch {
            repository.clearProductDetail()
        }
    }

    fun clearStoreDetail() {
        viewModelScope.launch {
            repository.clearStoreDetail()
        }
    }
}