package com.xc.brainstore.view.me

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.model.UserDetailRequest
import com.xc.brainstore.data.remote.response.UpdateUserDetailResponse
import com.xc.brainstore.data.remote.response.UserDetailResponse
import com.xc.brainstore.data.repository.UserRepository
import kotlinx.coroutines.launch

class MeViewModel(private val repository: UserRepository) : ViewModel() {
    val userDetail: LiveData<UserDetailResponse> = repository.userDetail
    val newUserDetail: LiveData<UpdateUserDetailResponse> = repository.newUserDetail
    val message: LiveData<String?> get() = repository.message

    fun getUserDetail() {
        viewModelScope.launch {
            repository.getUserDetail()
        }
    }

    fun putUserDetail(userDetailRequest: UserDetailRequest, context: Context) {
        viewModelScope.launch {
            repository.putUserDetail(userDetailRequest, context)
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