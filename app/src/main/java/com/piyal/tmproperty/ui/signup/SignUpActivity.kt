package com.piyal.tmproperty.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.piyal.tmproperty.databinding.ActivitySignUpBinding
import com.piyal.tmproperty.ui.login.LoginActivity
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels


@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModels()


    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val username = binding.nameSEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()) {
                if (pass == confirmPass) {
                    viewModel.signUp(email, pass, username)
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    // Handle success state
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                is UiState.Failure -> {
                    hideLoading()
                    // Handle failure state
                    Toast.makeText(this, "Sign Up Failed: ${state.error}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showLoading() {
        // Show loading UI
    }

    private fun hideLoading() {
        // Hide loading UI
    }
}
