package com.xc.brainstore.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xc.brainstore.data.local.pref.UserPreference
import com.xc.brainstore.data.remote.response.ErrorResponse
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.ProductsItem
import com.xc.brainstore.data.remote.response.SearchResponse
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class ProductRepository private constructor(private val userPreference: UserPreference) {

    private val _productList = MutableLiveData<List<ProductResponseItem>>()
    val productList: LiveData<List<ProductResponseItem>> = _productList

    private val _productData = MutableLiveData<List<ProductsItem?>?>()
    val productData: LiveData<List<ProductsItem?>?> = _productData

    private val _productDetail = MutableLiveData<ProductResponseItem?>()
    val productDetail: LiveData<ProductResponseItem?> = _productDetail

    private val _storeDetail = MutableLiveData<SellerResponse?>()
    val storeDetail: LiveData<SellerResponse?> = _storeDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    suspend fun getProduct() {
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getProduct()

        client.enqueue(object : Callback<List<ProductResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productList.value = response.body()
                    Log.d("Get Product Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Product Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Product Response", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getNewestProduct() {
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getNewestProduct()

        client.enqueue(object : Callback<List<ProductResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productList.value = response.body()
                    Log.d("Get Product Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Product Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Product Response", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getPopularProduct() {
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getPopularProduct()

        client.enqueue(object : Callback<List<ProductResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productList.value = response.body()
                    Log.d("Get Product Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Product Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Product Response", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getLocationProduct() {
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getLocationProduct()

        client.enqueue(object : Callback<List<ProductResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductResponseItem>>,
                response: Response<List<ProductResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _productList.value = response.body()
                    Log.d("Get Product Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Product Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ProductResponseItem>>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Product Response", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun searchProduct(productName: String) {
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getProductSearch(productName)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    if (searchResponse != null) {
                        if (searchResponse.totalResults == 0) {
                            _message.value = "No product found!"
                        } else {
                            val items = searchResponse.products
                            _productData.value = items
                        }
                    } else {
                        _message.value = "Response body is null"
                        Log.e("Search Product Response", "Response body is null")
                    }
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Search Product Response", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Search Product Response", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getProductDetail(id: Int?){
        _isLoading.value = true
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getProductDetail(id)

        client.enqueue(object : Callback<ProductResponseItem> {
            override fun onResponse(
                call: Call<ProductResponseItem>,
                response: Response<ProductResponseItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _productDetail.value = responseBody
                        Log.d("Product Detail", responseBody.toString())
                    } else {
                        Log.e("Get Product Detail", "Response body is null")
                    }
                    Log.d("Get Product Detail", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Product Detail", errorMessage)
                }
            }

            override fun onFailure(call: Call<ProductResponseItem>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Product Detail", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getStore(id: Int?) {
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getStoreDetail(id)

        client.enqueue(object : Callback<SellerResponse> {
            override fun onResponse(
                call: Call<SellerResponse>,
                response: Response<SellerResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _storeDetail.value = responseBody
                        Log.d("Store Detail", responseBody.toString())
                    } else {
                        Log.e("Get Store Detail", "Response body is null")
                    }
                    Log.d("Get Store Detail", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                    Log.e("Get Store Detail", errorMessage)
                }
            }

            override fun onFailure(call: Call<SellerResponse>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get Store Detail", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }


    fun clearSearch() {
        _productData.value = emptyList()
    }

    fun clearProductDetail() {
        _productDetail.value = null
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(userPreference: UserPreference): ProductRepository = instance ?: synchronized(this) {
            instance ?: ProductRepository(userPreference)
        }.also { instance = it }
    }
}