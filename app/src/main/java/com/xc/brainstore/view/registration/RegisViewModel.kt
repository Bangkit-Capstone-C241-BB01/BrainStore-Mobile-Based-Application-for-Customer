package com.xc.brainstore.view.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xc.brainstore.data.model.UserRegistModel
import com.xc.brainstore.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisViewModel(private val repository: UserRepository) : ViewModel() {

    val isLoading: LiveData<Boolean> = repository.isLoading
    val isRequestSuccessful: LiveData<Boolean> = repository.isRequestSuccessful
    val message: LiveData<String?> get() = repository.message

    fun getRegisterResponse(userRegistrationData: UserRegistModel) {
        viewModelScope.launch {
            repository.getRegisterResponse(userRegistrationData)
        }
    }
}