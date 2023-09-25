package com.piyal.tmproperty.repository

interface UserRepository {
    suspend fun signUpUser(email: String, password: String, username: String): Result<Unit>
}
