package com.piyal.tmproperty.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.piyal.tmproperty.R
import com.piyal.tmproperty.databinding.ActivitySignUpBinding
import com.piyal.tmproperty.viewmodels.SignUpViewModel
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    // Inject the ViewModel using Hilt
    @Inject
    lateinit var viewModel: SignUpViewModel

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Inject the ViewModel
        val viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.tvsignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val username = binding.nameSEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty() && username.isNotEmpty()) {
                if (pass == confirmPass) {
                    viewModel.signUpUser(email, pass, username).observe(this) { result ->
                        when (result) {
                            is Result.Success -> {
                                Toast.makeText(this, "SignUp Successfully", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
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
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
