package com.xc.brainstore.view.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.remote.response.UserDetailResponse
import com.xc.brainstore.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class MeViewModel(private val repository: UserRepository) : ViewModel() {
    val userDetail: LiveData<UserDetailResponse> = repository.userDetail
    val message: LiveData<String?> get() = repository.message

    fun getUserDetail() {
        viewModelScope.launch {
            repository.getUserDetail()
        }
    }

    fun putUserDetail(
        userImage: File,
        userName: String,
        userPhone: String,
        userAddress: String
    ) {
        viewModelScope.launch {
            repository.putUserDetail(userImage, userName, userPhone, userAddress)
        }
    }

    fun putUserDetailWithoutImg(
        userName: String,
        userPhone: String,
        userAddress: String
    ) {
        viewModelScope.launch {
            repository.putUserDetailWithoutImg(userName, userPhone, userAddress)
        }
    }

    fun clearMessage() {
        repository.clearMessage()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}