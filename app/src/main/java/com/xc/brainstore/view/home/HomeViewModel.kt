package com.xc.brainstore.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

//    private val _productList = MutableLiveData<List<ProductResponse>>()
//    val productList: LiveData<List<ProductResponse>> = _productList

//    private val _productData = MutableLiveData<List<ItemsItem>>()
//    val productData: LiveData<List<ItemsItem>> = _productData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val message: LiveData<String> get() = _message
    private val _message = MutableLiveData<String>()

    fun getProduct() {
        _isLoading.value = true
//        val client = ApiConfig.getApiService().getProduct()
//        client.enqueue(object : Callback<List<ProductResponse>> {
//            override fun onResponse(
//                call: Call<List<ProductResponse>>,
//                response: Response<List<ProductResponse>>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _productList.value = response.body()
//                } else {
//                    _message.value = "Failed: ${response.message()}"
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
//                _isLoading.value = false
//                _message.value = "Failed: ${t.message.toString()}"
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
    }

    fun searchProduct(name: String) {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getProductSearch(name)
//        client.enqueue(object : Callback<ProductResponse> {
//            override fun onResponse(
//                call: Call<ProductResponse>,
//                response: Response<ProductResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    val productResponse = response.body()
//                    if (productResponse != null) {
//                        if (productResponse.totalCount == 0) {
//                            _message.value = "No product found"
//                        } else {
//                            val items = productResponse.items
//                            _productData.value = items
//                        }
//                    } else {
//                        _message.value = "Response body is null"
//                        Log.e(TAG, "Response body is null")
//                    }
//                } else {
//                    _message.value = "Failed: ${response.message()}"
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
//                _isLoading.value = false
//                _message.value = "Failed: ${t.message.toString()}"
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
    }
    companion object {
        const val TAG = "HomeViewModel"
    }
}