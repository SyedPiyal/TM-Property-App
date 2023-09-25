package com.piyal.tmproperty.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.piyal.tmproperty.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun signUpUser(email: String, password: String, username: String): LiveData<Result<Unit>> {
        return liveData {
            emit(Result.Loading)
            try {
                val result = userRepository.signUpUser(email, password, username)
                emit(result)
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
    }
}
