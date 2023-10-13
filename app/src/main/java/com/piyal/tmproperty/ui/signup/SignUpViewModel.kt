package com.piyal.tmproperty.ui.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.piyal.tmproperty.util.UiState
import kotlinx.coroutines.launch



import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _signUpState = MutableLiveData<UiState<Unit>>()
    val signUpState: LiveData<UiState<Unit>> get() = _signUpState

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            _signUpState.value = UiState.Loading
            _signUpState.value = userRepository.signUpUser(email, password, username)
        }
    }
}
