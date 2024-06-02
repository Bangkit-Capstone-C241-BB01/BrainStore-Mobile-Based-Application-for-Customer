package com.xc.brainstore.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailProductViewModel : ViewModel() {

//    private val _productDetail = MutableLiveData<ProductDetailResponse?>()
//    val productDetail: LiveData<ProductDetailResponse?> = _productDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val message: LiveData<String> get() = _message
    private val _message = MutableLiveData<String>()

    fun getProductDetail(id: Int){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getProduct(id)
//
//        client.enqueue(object : Callback<ProductDetailResponse> {
//            override fun onResponse(
//                call: Call<ProductDetailResponse>,
//                response: Response<ProductDetailResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body()?.error == false) {
//                    val responseBody = response.body()
//                    if (responseBody != null) {
//                        _productDetail.value = responseBody
//                    } else {
//                        Log.e(TAG, "Response body is null")
//                    }
//                    Log.d(TAG, "Successful")
//                } else {
//                    val errorMessage = "Failed to fetch product detail: ${response.message()}"
//                    Log.e(TAG, errorMessage)
//                }
//            }
//
//            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
//                _isLoading.value = false
//                val errorMessage = "Failed: ${t.message}"
//                Log.e(TAG, errorMessage)
//            }
//        })
    }

    companion object {
        private const val TAG = "Product Detail ViewModel"
    }
}