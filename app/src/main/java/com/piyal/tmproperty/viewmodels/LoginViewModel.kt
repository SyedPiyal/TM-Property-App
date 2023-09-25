package com.piyal.tmproperty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.piyal.tmproperty.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun signInUser(email: String, password: String): LiveData<Result<Unit>> {
        return liveData {
            emit(Result.Loading)
            val result = userRepository.signInUser(email, password)
            emit(result)
        }
    }

    fun sendPasswordResetEmail(email: String): LiveData<Result<Unit>> {
        return liveData {
            emit(Result.Loading)
            val result = userRepository.sendPasswordResetEmail(email)
            emit(result)
        }
    }
}
