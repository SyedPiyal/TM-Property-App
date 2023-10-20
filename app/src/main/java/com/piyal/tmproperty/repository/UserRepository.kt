package com.piyal.tmproperty.repository

import com.piyal.tmproperty.data.User
import com.piyal.tmproperty.util.UiState
import javax.inject.Inject
import android.content.Context

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

    suspend fun signOutUser(): UiState<Unit> {
        return try {
            userService.signOutUser() // Call the appropriate function from your UserService to sign out the user.
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

    suspend fun getUser(userId: String): UiState<User> {
        return try {
            val user = userService.getUser(userId)
            UiState.Success(user)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

    suspend fun updateUser(userId: String, newUsername: String): UiState<User> {
        return try {
            userService.updateUser(userId, newUsername)
            val updatedUser = userService.getUser(userId)
            UiState.Success(updatedUser)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
    // Function to check if the user is logged in
    suspend fun isUserLoggedIn(context: Context): Boolean {
        // Assuming userService.isUserLoggedIn() performs the necessary logic.
        // For example, it might involve checking if there's a valid user session on the backend.
        return userService.isUserLoggedIn(context)
    }
}
