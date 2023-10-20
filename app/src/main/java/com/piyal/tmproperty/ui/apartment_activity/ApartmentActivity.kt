package com.piyal.tmproperty.ui.apartment_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.databinding.ActivityApartmentBinding
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}