package com.piyal.tmproperty.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.databinding.ActivityLoginBinding
import com.piyal.tmproperty.ui.NavigationActivity
import com.piyal.tmproperty.ui.signup.SignUpActivity
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)

        binding.tvregis.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                viewModel.signIn(email, pass)
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        /*viewModel.loginState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user?.isEmailVerified == true) {
                        // User email is verified, proceed to the main activity
                        val intent = Intent(this, NavigationActivity::class.java)
                        startActivity(intent)
                        finish()
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()
                    } else {
                        Toast.makeText(this, "Please verify your email first.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is UiState.Failure -> {
                    hideLoading()
                    Toast.makeText(this, "Login Failed: ${state.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    // Proceed to the main activity
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                }

                is UiState.Failure -> {
                    hideLoading()
                    Toast.makeText(this, "Login Failed: ${state.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun showLoading() {
        // Show loading UI if needed
    }

    private fun hideLoading() {
        // Hide loading UI if needed
    }
}
