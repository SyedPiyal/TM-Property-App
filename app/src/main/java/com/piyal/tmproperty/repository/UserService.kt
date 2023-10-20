package com.piyal.tmproperty.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.piyal.tmproperty.data.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject




class UserService @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

   /* suspend fun signUpUser(email: String, password: String, username: String) {
        try {
            // Create user in Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userDetails = hashMapOf(
                        "username" to username,
                        "email" to email
                        // Add more user details if needed
                    )

                    // Store user details in Firestore
                    user?.uid?.let {
                        firestore.collection("users").document(it)
                            .set(userDetails)
                            .addOnSuccessListener {
                                // User registration successful
                            }
                            .addOnFailureListener { exception ->
                                // User registration failed
                                throw Exception("Firestore Error: ${exception.message}")
                            }
                    } ?: throw Exception("User is null after authentication.")
                } else {
                    // User registration failed
                    throw Exception("Firebase Auth Error: ${authTask.exception?.message}")
                }
            }.await()
        } catch (e: Exception) {
            // Handle exceptions or rethrow as needed
            throw e
        }
    }*/

    suspend fun signUpUser(email: String, password: String, username: String) {
        try {
            // Create user in Firebase Authentication without email verification
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        val userDetails = hashMapOf(
                            "username" to username,
                            "email" to email
                            // Add more user details if needed
                        )

                        // Store user details in Firestore
                        user?.uid?.let {
                            firestore.collection("users").document(it)
                                .set(userDetails)
                                .addOnSuccessListener {
                                    // User registration successful
                                }
                                .addOnFailureListener { exception ->
                                    // User registration failed
                                    throw Exception("Firestore Error: ${exception.message}")
                                }
                        } ?: throw Exception("User is null after authentication.")
                    } else {
                        // User registration failed
                        throw Exception("Firebase Auth Error: ${authTask.exception?.message}")
                    }
                }.await()
        } catch (e: Exception) {
            // Handle exceptions or rethrow as needed
            throw e
        }
    }


    suspend fun signInUser(email: String, password: String): Boolean {
        return try {
            // Sign in user using Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            true // Sign-in successful
        } catch (e: Exception) {
            // Handle exceptions or rethrow as needed
            false // Sign-in failed
        }
    }

    suspend fun signOutUser(): Boolean {
        return try {
            firebaseAuth.signOut() // Sign out the currently authenticated user
            true // Sign-out successful
        } catch (e: Exception) {
            // Handle exceptions or rethrow as needed
            false // Sign-out failed
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Boolean {
        return try {
            // Send password reset email using Firebase Authentication
            firebaseAuth.sendPasswordResetEmail(email).await()
            true // Email sent successfully
        } catch (e: Exception) {
            // Handle exceptions or rethrow as needed
            false // Failed to send email
        }
    }

    suspend fun getUser(userId: String): User {
        return try {
            val documentSnapshot = firestore.collection("users").document(userId).get().await()
            if (documentSnapshot.exists()) {
                val username = documentSnapshot.getString("username") ?: ""
                val email = documentSnapshot.getString("email") ?: ""
                User(username, email)
            } else {
                // Handle the case where the user document does not exist
                User("", "")
            }
        } catch (e: Exception) {
            // Handle exceptions here
            throw e
        }
    }

    suspend fun updateUser(userId: String, newUsername: String) {
        try {
            val userRef = firestore.collection("users").document(userId)
            userRef.update("username", newUsername).await()
        } catch (e: Exception) {
            // Handle exceptions here
            throw e
        }
    }

    // Simulates a network request to check if the user is logged in.
    // In a real-world scenario, you would make an actual network request to your backend.
    // Pass a Context parameter to the function
    suspend fun isUserLoggedIn(context: Context): Boolean {
        // Access SharedPreferences using the provided Context
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Check Firebase authentication if SharedPreferences indicates user is not logged in
        if (!isLoggedIn) {
            return firebaseAuth.currentUser != null
        }

        return isLoggedIn
    }

}
