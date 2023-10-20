package com.piyal.tmproperty.ui.logout



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.piyal.tmproperty.databinding.FragmentLogOutBinding
import com.piyal.tmproperty.ui.login.LoginActivity
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogOutFragment : Fragment() {

    private val viewModel: LogoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentLogOutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Logout")
            .setMessage("Do you want to log out?")
            .setPositiveButton("Yes") { dialog, which ->
                viewModel.logout()
            }
            .setNegativeButton("No") { dialog, which ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .show()

        viewModel.logoutState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show loading UI if needed
                }
                is UiState.Success -> {
                    // Navigate to the login screen or perform other actions
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is UiState.Failure -> {
                    // Handle error state, show error message, etc.
                }
            }
        }

        return root
    }


}

