package com.piyal.tmproperty.ui.user_profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.databinding.FragmentUserProfileBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModels()
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        viewModel.getUser(userId!!)

        viewModel.userState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    val user = state.data
                    binding.usernameEt.setText(user.username)
                    binding.emailTv.text = user.email
                }
                is UiState.Failure -> {
                    hideLoading()
                    val errorMessage = state.error ?: "Unknown error occurred"
                    Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.btnSave.setOnClickListener {
            val newUsername = binding.usernameEt.text.toString()
            viewModel.updateUser(userId, newUsername)
        }

        return root
    }

    private fun showLoading() {
        // Show loading UI if needed
    }

    private fun hideLoading() {
        // Hide loading UI if needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
