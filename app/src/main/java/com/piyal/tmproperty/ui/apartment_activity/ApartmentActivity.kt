package com.piyal.tmproperty.ui.apartment_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.databinding.ActivityApartmentBinding
import com.piyal.tmproperty.ui.details_activity.DetailsActivity
import com.piyal.tmproperty.util.PropertyNavigationUtil
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApartmentActivity : AppCompatActivity() {

    private val viewModel: ApartmentViewModel by viewModels()
    private var _binding: ActivityApartmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var featuredPropertyAdapter: FeaturedPropertyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityApartmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Apartment List"

        // Initialize featuredPropertyAdapter for featured properties
        featuredPropertyAdapter = FeaturedPropertyAdapter(emptyList()) { property ->
            // Handle featured property item click here
            openPropertyDetails(property)
        }

        binding.rvApartment.apply {
            layoutManager = LinearLayoutManager(this@ApartmentActivity, RecyclerView.HORIZONTAL, false)
            adapter = featuredPropertyAdapter
        }

        viewModel.apartmentList.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    featuredPropertyAdapter.updateProperties(state.data)
                }

                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state for featured properties
                }
            }
        }

        viewModel.loadApartments() // Load featured properties when fragment is created
    }

    private fun showLoading() {
        // Show loading UI if needed
    }

    private fun hideLoading() {
        // Hide loading UI if needed
    }
    private fun openPropertyDetails(property: Property) {
        // Handle navigation to property details activity here
        /*val intent = Intent(this, DetailsActivity::class.java).apply {
            // Pass property details to DetailsActivity
            putExtra("bed", property.titleBedNumber)
            putExtra("bath", property.titleBathNumber)
            putExtra("square", property.titleSquareNumber)
            putExtra("address", property.addressitm)
            putExtra("type", property.itemType)
            putExtra("price", property.tvPrice)
            putExtra("userId", property.userId)
            putExtra("postId", property.postId)
            putExtra("imageUrls", ArrayList(property.imageUrls))
            putExtra("title", property.titleitm)
            putExtra("purpose", property.purpose)
            putExtra("description", property.description)
            putExtra("contact", property.contact)
            putExtra("postedDateTime", property.postedDateTime)
        }
        startActivity(intent)*/

        PropertyNavigationUtil.openPropertyDetails(this, property)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}