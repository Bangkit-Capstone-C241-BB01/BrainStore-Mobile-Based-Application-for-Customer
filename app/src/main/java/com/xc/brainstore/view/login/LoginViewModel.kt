package com.xc.brainstore.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.model.UserLoginModel
import com.xc.brainstore.data.model.UserModel
import com.xc.brainstore.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading
    val message: LiveData<String?> get() = repository.message
    val tokenUser: LiveData<String?> get() = repository.tokenUser

    fun getLoginResponse(userLoginData: UserLoginModel) {
        viewModelScope.launch {
            repository.getLoginResponse(userLoginData)
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun clearMessage() {
        repository.clearMessage()
    }
}