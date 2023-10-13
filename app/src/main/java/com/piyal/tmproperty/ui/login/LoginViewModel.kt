package com.piyal.tmproperty.ui.login

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
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _loginState = MutableLiveData<UiState<Unit>>()
    val loginState: LiveData<UiState<Unit>> get() = _loginState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            _loginState.value = userRepository.signInUser(email, password)
        }
    }
}
