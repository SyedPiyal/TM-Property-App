package com.piyal.tmproperty.ui.update_activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.piyal.tmproperty.adapters.ImageAdapter
import com.piyal.tmproperty.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private val viewModel: UpdateViewModel by viewModels()
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var postId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Update Details"
        postId = intent.getStringExtra("postId") ?: ""

        // Set up RecyclerView
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        val adapter = ImageAdapter(viewModel.selectedImages.value.orEmpty())
        recyclerView.adapter = adapter

        // Observe selected images LiveData for updates
        viewModel.selectedImages.observe(this) { selectedImages ->
            adapter.updateData(selectedImages)
        }

        // Handle image picking and permissions
        viewModel.handleImagePicking(this)

        // Observe permission granted LiveData for updates
        viewModel.permissionGranted.observe(this) { isGranted ->
            if (isGranted) {
                // Permission granted, handle UI interactions or perform actions if needed
            } else {
                // Permission denied, handle UI interactions or show error message
                Toast.makeText(this, "Permission denied. Cannot pick images.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle update button click
        binding.btnUpdate.setOnClickListener {
            updatePost()
        }
    }

    private fun updatePost() {
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

        // Perform the update operation using ViewModel
        viewModel.updatePost(postId, type, purpose, price, bed, bath, square, location, title, description, contact)
    }
}
