package com.piyal.tmproperty.ui.addproperty


import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import com.piyal.tmproperty.databinding.FragmentAddPropertyBinding
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.adapters.ImageAdapter
import com.piyal.tmproperty.data.PropertyData
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class AddPropertyFragment : Fragment() {

    private val viewModel: AddPropertyViewModel by viewModels()
    private lateinit var binding: FragmentAddPropertyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle image picking and permissions
        viewModel.handleImagePicking(this)

        // Observe selected images LiveData for updates
        viewModel.selectedImages.observe(viewLifecycleOwner) { selectedImages ->
            // Update UI or perform actions based on selectedImages
        }

        // Observe permission granted LiveData for updates
        viewModel.permissionGranted.observe(viewLifecycleOwner) { isGranted ->
            if (isGranted) {
                // Permission granted, handle UI interactions or perform actions if needed
            } else {
                // Permission denied, handle UI interactions or show error message
            }
        }

        binding.btnAdd.setOnClickListener {
            // Get property details from UI input fields
            val type = binding.etType.text.toString().trim()
            val purpose = binding.etPurpose.text.toString().trim()
            val price = binding.etPrice.text.toString().trim()
            val bed = binding.etBed.text.toString().trim()
            val bath = binding.etBath.text.toString().trim()
            val square = binding.etSquere.text.toString().trim()
            val location = binding.etLocation.text.toString().trim()
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val contact = binding.etContact.text.toString().trim()

            // Check if any of the fields are empty
            if (type.isNotEmpty() && purpose.isNotEmpty() && price.isNotEmpty() && bed.isNotEmpty() && bath.isNotEmpty()
                && square.isNotEmpty() && location.isNotEmpty() && title.isNotEmpty() && description.isNotEmpty()
                && contact.isNotEmpty()
            ) {
                // obtain the current user's ID if needed
                val currentUser = FirebaseAuth.getInstance().currentUser?.uid

                // Get the current date and time
                val currentDateTime = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
                val formattedDateTime = currentDateTime.format(formatter)

                // Create PropertyData object
                val propertyData = PropertyData(
                    type, purpose, price, bed, bath, square, location, title, description, contact,
                    currentUser ?: "", formattedDateTime, viewModel.selectedImages.value.orEmpty().map { it.toString() }
                )

                // Add property using ViewModel
                viewModel.addProperty(propertyData)
            } else {
                Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.propertyAdded.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    // Handle success state
                }
                is UiState.Failure -> {
                    hideLoading()
                    // Handle failure state
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up resources, if any
    }
}


