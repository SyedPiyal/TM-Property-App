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
import com.piyal.tmproperty.adapters.ImageAdapter
import com.piyal.tmproperty.data.PropertyData
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddPropertyFragment : Fragment() {

    private val viewModel: AddPropertyViewModel by viewModels()
    private var _binding: FragmentAddPropertyBinding? = null
    private val binding get() = _binding!!

    private val selectedImages: MutableList<Uri> = mutableListOf()

    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initialView()
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initialView() {
        // Setup UI components and click listeners

        binding.btnAdd.setOnClickListener {
            // Extract data from UI fields
            val propertyData = PropertyData(
                type = binding.etType.text.toString().trim(),
                purpose = binding.etPurpose.text.toString().trim(),
                price = binding.etPrice.text.toString().trim(),
                bed = binding.etBed.text.toString().trim(),
                bath = binding.etBath.text.toString().trim(),
                square = binding.etSquere.text.toString().trim(),
                location = binding.etLocation.text.toString().trim(),
                title = binding.etTitle.text.toString().trim(),
                description = binding.etDescription.text.toString().trim(),
                contact = binding.etContact.text.toString().trim()
                // ... (add other fields as needed)
            )

            // Validate and add property
            viewModel.addProperty(propertyData, selectedImages)
            observePropertyState()
        }

        // Handle image picker and permission logic
        binding.btnAddImage.setOnClickListener {
            checkPermissionAndOpenImagePicker()
        }

        // Setup RecyclerView adapter for selected images
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageAdapter(selectedImages)
    }

    private fun checkPermissionAndOpenImagePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val permissionGranted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(requireContext(), permission) == permissionGranted) {
            openImagePicker()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), IMAGE_REQUEST_CODE)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openImagePicker()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val clipData = data.clipData
                for (i in 0 until clipData?.itemCount!!) {
                    val imageUri = clipData?.getItemAt(i)!!.uri
                    selectedImages.add(imageUri)
                }
            } else if (data?.data != null) {
                val imageUri = data.data
                imageUri?.let { selectedImages.add(it) }
            }

            // Notify the RecyclerView adapter about the data change
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }



    private fun observePropertyState() {
        viewModel.propertyState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    // Handle success, navigate to another screen if needed, show a success message, etc.
                }
                is UiState.Failure -> {
                    hideLoading()
                    // Handle failure, show an error message, etc.
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

    // Handle image picker and permission request logic (not shown for brevity)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

