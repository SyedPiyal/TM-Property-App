package com.piyal.tmproperty.ui.details_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.R
import com.piyal.tmproperty.adapters.ImageSliderAdapter
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.databinding.ActivityDetailsBinding
import com.piyal.tmproperty.ui.update_activity.UpdateActivity
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

/*@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: PropertyDetailsViewModel by viewModels()
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Extract postId from intent
        val postId = intent.getStringExtra("postId")

        // Observe property details from ViewModel
        viewModel.propertyDetails.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    bindPropertyDetails(state.data)
                }
                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state for property details
                    Toast.makeText(this, "Failed to load details", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Load property details
        if (postId != null) {
            viewModel.loadPropertyDetails(postId)
        }

        // Other setup code...
    }

    private fun showLoading() {
        // Show loading UI if needed
    }

    private fun hideLoading() {
        // Hide loading UI if needed
    }

    private fun bindPropertyDetails(property: Property) {
        // Update UI with property details
        binding.apply {
            tvTittleDetails.text = property.titleitm
            tvLocationDetails.text = property.addressitm
            tvAmountDetails.text = property.tvPrice
            tvDetailsTtitle.text = property.purpose
            tvDetalisPerposeType.text = property.purpose
            tvBedNumberD.text = property.titleBedNumber
            tvBathNumberD.text = property.titleBathNumber
            tvSquareNumber.text = property.titleSquareNumber
            tvDescriptionDetails.text = property.description
            tvDetalisContactInfo.text = property.contact
            tvPostedDateTime.text = property.postedDateTime

            // Load property images into a ViewPager or other image view
            val viewPager: ViewPager2 = findViewById(R.id.viewPager)
            val imageUrls = property.imageUrls

            if (imageUrls.isNotEmpty()) {
                val imageSliderAdapter = ImageSliderAdapter(imageUrls)
                viewPager.adapter = imageSliderAdapter
            } else {
                // Handle the case where there are no image URLs in the list
                // You can set a default image or hide the ViewPager, etc.
            }

            // Set up click listeners or other interactions as needed

            btnPrevious.setOnClickListener {
                viewPager.currentItem = viewPager.currentItem - 1
            }

            btnNext.setOnClickListener {
                viewPager.currentItem = viewPager.currentItem + 1
            }
            //back buuton
            btnBack.setOnClickListener {
                finish() // Finish the current activity and return to the previous one
            }

            // Set up click listeners or other interactions as needed
            // Button for updating the property
            btnUpdate.setOnClickListener {
                val intent = Intent(this@DetailsActivity, UpdateActivity::class.java)
                intent.putExtra("postId", property.postId)
                startActivity(intent)
            }

            // Button for deleting the property
            btnDelete.setOnClickListener {
                showDeleteConfirmationDialog(property.postId)
            }

            // Button for favoriting/unfavoriting the property
            val favoriteButton = btnFavorite
            val unfavoriteButton = btnUnFavorite

            // Initially hide the unfavoriteButton
            unfavoriteButton.visibility = View.GONE

            // Check if the property is favorited
            checkIfFavorited(property.postId) { isFavorite ->
                if (isFavorite) {
                    favoriteButton.visibility = View.GONE
                    unfavoriteButton.visibility = View.VISIBLE
                } else {
                    favoriteButton.visibility = View.VISIBLE
                    unfavoriteButton.visibility = View.GONE
                }
            }

            favoriteButton.setOnClickListener {
                toggleFavorite(property.postId)
            }

            unfavoriteButton.setOnClickListener {
                toggleFavorite(property.postId)
            }
        }
    }


    // ... other methods
    private fun showDeleteConfirmationDialog(postId: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this property?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                // Perform the delete operation
                deleteProperty(postId)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun deleteProperty(postId: String) {
        viewModel.deleteProperty(postId) // Assuming you have a deleteProperty method in your ViewModel
        finish() // Finish the current activity and return to the previous one
    }

    private fun checkIfFavorited(postId: String, callback: (Boolean) -> Unit) {
        // Implement logic to check if the property is favorited
        // You might use the ViewModel or another repository method for this
        //viewModel.checkIfFavorited(postId, callback)
        viewModel.checkIfFavorited(postId) { isFavorite: Boolean ->
            if (isFavorite) {
                // Handle the case where the property is favorited
            } else {
                // Handle the case where the property is not favorited
            }
        }


    }

    private fun toggleFavorite(postId: String) {
        // Implement logic to toggle favorite status
        // You might use the ViewModel or another repository method for this
        viewModel.toggleFavorite(postId)
    }
}*/


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: PropertyDetailsViewModel by viewModels()
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postId = intent.getStringExtra("postId")
        val postOwnerId = intent.getStringExtra("userId")
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        // Compare post owner ID with current user ID
        if (postOwnerId == currentUserId) {
            // Show the buttons for the post owner
            binding.btnUpdate.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE


        } else {
            // Hide the buttons for other users
            binding.btnUpdate.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE

        }

        if (postId != null) {
            viewModel.getPropertyDetails(postId)
            if (currentUserId != null) {
                viewModel.checkIfFavorited(currentUserId, postId)
            }

            viewModel.propertyDetails.observe(this) { propertyState ->
                when (propertyState) {
                    is UiState.Loading -> showLoading()
                    is UiState.Success -> {
                        hideLoading()
                        updateUI(propertyState.data)
                    }

                    is UiState.Failure -> {
                        hideLoading()
                        showError(propertyState.error)
                    }
                }
            }



            viewModel.checkIfFavoritedState.observe(this) { favoritedState ->
                when (favoritedState) {
                    is UiState.Loading -> showLoading()
                    is UiState.Success -> {
                        hideLoading()
                        updateFavoriteUI(favoritedState.data)
                    }

                    is UiState.Failure -> {
                        hideLoading()
                        //showError(favoritedState.message)
                    }
                }
            }
        }

        binding.btnFavorite.setOnClickListener {
            if (postId != null && currentUserId != null) {
                viewModel.toggleFavorite(currentUserId, postId)
            }
        }

        binding.btnUnFavorite.setOnClickListener {
            if (postId != null && currentUserId != null) {
                viewModel.toggleFavorite(currentUserId, postId)
            }
        }

        // Other existing code...
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnUpdate.setOnClickListener {
            // Handle update option, navigate to the UpdateActivity with the propertyId
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("postId", postId)
            startActivity(intent)
            finish()
        }

        binding.btnDelete.setOnClickListener {
            // Handle delete option, show a confirmation dialog before deleting the post
            if (postId != null) {
                showDeleteConfirmationDialog(postId)
            }
        }
    }

    private fun updateUI(property: Property) {
        // Update UI with property details
        // ...
        binding.apply {
            tvTittleDetails.text = property.titleitm
            tvLocationDetails.text = property.addressitm
            tvAmountDetails.text = property.tvPrice
            tvDetailsTtitle.text = property.purpose
            tvDetalisPerposeType.text = property.purpose
            tvBedNumberD.text = property.titleBedNumber
            tvBathNumberD.text = property.titleBathNumber
            tvSquareNumber.text = property.titleSquareNumber
            tvDescriptionDetails.text = property.description
            tvDetalisContactInfo.text = property.contact
            tvPostedDateTime.text = property.postedDateTime

            // Load property images into a ViewPager or other image view
            val viewPager: ViewPager2 = findViewById(R.id.viewPager)
            val imageUrls = property.imageUrls

            if (imageUrls.isNotEmpty()) {
                val imageSliderAdapter = ImageSliderAdapter(imageUrls)
                viewPager.adapter = imageSliderAdapter
            } else {
                // Handle the case where there are no image URLs in the list
                // You can set a default image or hide the ViewPager, etc.
            }

            // Set up click listeners or other interactions as needed

            btnPrevious.setOnClickListener {
                viewPager.currentItem = viewPager.currentItem - 1
            }

            btnNext.setOnClickListener {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }
    }
    private fun showDeleteConfirmationDialog(postId: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this post?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                // Perform the delete operation
                viewModel.deleteProperty(postId)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun updateFavoriteUI(isFavorite: Boolean) {
        // Update UI based on favorite status
        if (isFavorite) {
            // Handle UI for favorited state
            binding.btnFavorite.visibility = View.GONE
            binding.btnUnFavorite.visibility = View.VISIBLE
        } else {
            // Handle UI for not favorited state
            binding.btnFavorite.visibility = View.VISIBLE
            binding.btnUnFavorite.visibility = View.GONE
        }
    }


    private fun showLoading() {
        // Implement loading UI
    }

    private fun hideLoading() {
        // Implement hide loading UI
    }

    private fun showError(message: String?) {
        // Implement error UI
    }
}
