package com.piyal.tmproperty.ui.house_activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piyal.tmproperty.R
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.databinding.ActivityHouseBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HouseActivity : AppCompatActivity() {

    private val viewModel: HouseViewModel by viewModels()
    private var _binding: ActivityHouseBinding? = null
    private val binding get() = _binding!!

    private lateinit var featuredPropertyAdapter: FeaturedPropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)




        supportActionBar?.title = "House List"

        // Initialize featuredPropertyAdapter for featured properties
        featuredPropertyAdapter = FeaturedPropertyAdapter(emptyList()) { property ->
            // Handle featured property item click here
        }

        binding.rvHouse.apply {
            layoutManager = LinearLayoutManager(this@HouseActivity, RecyclerView.HORIZONTAL, false)
            adapter = featuredPropertyAdapter
        }

        viewModel.houseList.observe(this) { state ->
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

        viewModel.loadHouses() // Load featured properties when activity is created
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
