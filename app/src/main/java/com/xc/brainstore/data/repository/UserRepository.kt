package com.xc.brainstore.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xc.brainstore.data.local.pref.UserPreference
import com.xc.brainstore.data.model.UserDetailRequest
import com.xc.brainstore.data.model.UserLoginModel
import com.xc.brainstore.data.model.UserModel
import com.xc.brainstore.data.model.UserRegistModel
import com.xc.brainstore.data.remote.response.ErrorResponse
import com.xc.brainstore.data.remote.response.LoginResponse
import com.xc.brainstore.data.remote.response.RegisterResponse
import com.xc.brainstore.data.remote.response.UpdateUserDetailResponse
import com.xc.brainstore.data.remote.response.UserDetailResponse
import com.xc.brainstore.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tokenUser = MutableLiveData<String?>()
    val tokenUser: LiveData<String?> get() = _tokenUser

    private val _userDetail = MutableLiveData<UserDetailResponse>()
    val userDetail: LiveData<UserDetailResponse> = _userDetail

    private val _newUserDetail = MutableLiveData<UpdateUserDetailResponse>()
    val newUserDetail: LiveData<UpdateUserDetailResponse> = _newUserDetail

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    fun getRegisterResponse(userRegistrationData: UserRegistModel) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(
            userRegistrationData.name,
            userRegistrationData.email,
            userRegistrationData.password,
            userRegistrationData.role
        )

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val successMessage = it.msg
                        successMessage?.let {
                            _message.value = successMessage
                        }
                    }
                    Log.d("Regis Response Status", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection!"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Post Register", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    fun getLoginResponse(userLoginData: UserLoginModel) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(
            userLoginData.email,
            userLoginData.password
        )

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        val successMessage = it.msg
                        val tokenUser = it.token

                        successMessage?.let {
                            _message.value = successMessage
                        }

                        tokenUser?.let {
                            _tokenUser.value = tokenUser
                        }
                    }
                    Log.d("Login Response Status", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Post Login", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun getUserDetail() {
        val token = userPreference.getLoginSession().first().token
        val client = ApiConfig.getApiService(token).getUserDetail()

        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                    Log.d("Get User Detail Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Get User Detail", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    suspend fun putUserDetail(userDetailRequest: UserDetailRequest, context: Context) {
        val token = userPreference.getLoginSession().first().token
        Log.d("User Img Path", userDetailRequest.userImg ?: "User img is null")

        val imgUri = Uri.parse(userDetailRequest.userImg)
        val imageFile = com.xc.brainstore.utils.uriToFile(imgUri, context)
        Log.d("ImageFile", imageFile.path)

        val updatedUserDetailRequest = userDetailRequest.copy(userImg = imageFile.toString())

        val client = ApiConfig.getApiService(token).putUserDetail(updatedUserDetailRequest)

        client.enqueue(object : Callback<UpdateUserDetailResponse> {
            override fun onResponse(
                call: Call<UpdateUserDetailResponse>,
                response: Response<UpdateUserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    _newUserDetail.value = response.body()
                    response.body()?.let {
                        val successMessage = it.msg
                        successMessage?.let {
                            _message.value = successMessage
                        }
                    }
                    Log.d("Put User Detail Response", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                }
            }

            override fun onFailure(call: Call<UpdateUserDetailResponse>, t: Throwable) {
                when (t) {
                    is UnknownHostException -> {
                        _message.value = "No Internet Connection"
                        Log.e("UnknownHostException", "onFailure: ${t.message.toString()}")
                    }

                    else -> {
                        _message.value = t.message.toString()
                        Log.e("Put User Detail", "onFailure: ${t.message.toString()}")
                    }
                }
            }
        })
    }

    fun clearMessage() {
        _message.value = null
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveLoginSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getLoginSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }

}