package com.piyal.tmproperty.repository

import com.piyal.tmproperty.util.UiState
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService) {

    suspend fun signUpUser(email: String, password: String, username: String): UiState<Unit> {
        return try {
            userService.signUpUser(email, password, username)
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
    suspend fun signInUser(email: String, password: String): UiState<Unit> {
        return try {
            userService.signInUser(email, password)
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
}
