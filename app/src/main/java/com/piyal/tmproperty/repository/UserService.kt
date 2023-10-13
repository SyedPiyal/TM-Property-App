package com.piyal.tmproperty.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject




class UserService @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    suspend fun signUpUser(email: String, password: String, username: String) {
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
}
