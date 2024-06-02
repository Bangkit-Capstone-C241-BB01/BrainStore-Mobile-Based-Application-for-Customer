package com.xc.brainstore.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.xc.brainstore.data.local.pref.UserPreference
import com.xc.brainstore.data.model.UserLoginModel
import com.xc.brainstore.data.model.UserModel
import com.xc.brainstore.data.model.UserRegistModel
import com.xc.brainstore.data.remote.response.ErrorResponse
import com.xc.brainstore.data.remote.response.LoginResponse
import com.xc.brainstore.data.remote.response.RegisterResponse
import com.xc.brainstore.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRequestSuccessful = MutableLiveData<Boolean>()
    val isRequestSuccessful: LiveData<Boolean> = _isRequestSuccessful

    private val _tokenUser = MutableLiveData<String?>()
    val tokenUser: LiveData<String?> get() = _tokenUser

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
                    _isRequestSuccessful.value = true
                    Log.d("Regis Response Status", "Successful")
                } else {
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    Log.d("Regis Response", errorMessage)
                    _message.value = errorMessage
                }
                _message.value = ""
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
                    _isRequestSuccessful.value = true
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
                    _isRequestSuccessful.value = false
                    val jsonInString = response.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                    val errorMessage = errorBody?.message ?: response.message()
                    _message.value = errorMessage
                }
                _message.value = ""
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isRequestSuccessful.value = false
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