package com.piyal.tmproperty.ui.logout


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.repository.UserRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _logoutState = MutableLiveData<UiState<Unit>>()
    val logoutState: LiveData<UiState<Unit>> get() = _logoutState

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = UiState.Loading
            _logoutState.value = userRepository.signOutUser() // Assume you have a function like this in UserRepository
        }
    }
}
