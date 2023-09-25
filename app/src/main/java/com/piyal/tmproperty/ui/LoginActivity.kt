package com.piyal.tmproperty.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.R
import com.piyal.tmproperty.databinding.ActivityLoginBinding
import com.piyal.tmproperty.viewmodels.LoginViewModel
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    // Inject the ViewModel using Hilt
    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Inject the ViewModel
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.tvregis.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.signInUser(email, pass).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            val user = FirebaseAuth.getInstance().currentUser
                            if (user?.isEmailVerified == true) {
                                val intent = Intent(this, NavigationActivity::class.java)
                                startActivity(intent)
                                finish()
                                val sharedPreferences =
                                    getSharedPreferences("login_status", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("isLoggedIn", true)
                                editor.apply()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Please verify your email first.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        is Result.Error -> {
                            Toast.makeText(
                                this,
                                "Error: ${result.exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Loading -> {
                            // Show loading indicator if needed
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvforgetPassword.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()

            if (email.isNotEmpty()) {
                viewModel.sendPasswordResetEmail(email).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is Result.Error -> {
                            Toast.makeText(
                                this,
                                "Failed to send password reset email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Result.Loading -> {
                            // Show loading indicator if needed
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter your email address to reset the password.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
