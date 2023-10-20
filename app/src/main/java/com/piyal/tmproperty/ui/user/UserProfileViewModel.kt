package com.piyal.tmproperty.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.data.User
import com.piyal.tmproperty.repository.UserRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _userState = MutableLiveData<UiState<User>>()
    val userState: LiveData<UiState<User>> get() = _userState

    fun getUser(userId: String) {
        viewModelScope.launch {
            _userState.value = UiState.Loading
            _userState.value = userRepository.getUser(userId)
        }
    }

    fun updateUser(userId: String, newUsername: String) {
        viewModelScope.launch {
            _userState.value = UiState.Loading
            _userState.value = userRepository.updateUser(userId, newUsername)
        }
    }
}
