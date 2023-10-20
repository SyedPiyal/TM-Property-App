package com.piyal.tmproperty.ui.featuredlist_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.databinding.ActivityFeaturedListBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeaturedListActivity : AppCompatActivity() {

    private val viewModel: FeaturedListViewModel by viewModels()
    private var _binding: ActivityFeaturedListBinding? = null
    private val binding get() = _binding!!

    private lateinit var featuredPropertyAdapter: FeaturedPropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFeaturedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Featured Properties"

        // Initialize featuredPropertyAdapter for featured properties
        featuredPropertyAdapter = FeaturedPropertyAdapter(emptyList()) { property ->
            // Handle featured property item click here
        }

        binding.rvFeaturedList.apply {
            layoutManager = LinearLayoutManager(this@FeaturedListActivity)
            adapter = featuredPropertyAdapter
        }

        viewModel.featuredList.observe(this) { state ->
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

        viewModel.loadFeaturedProperties() // Load featured properties when activity is created
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
