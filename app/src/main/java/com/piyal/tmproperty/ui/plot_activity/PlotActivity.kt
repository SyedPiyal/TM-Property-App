package com.piyal.tmproperty.ui.plot_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.databinding.ActivityPlotBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlotActivity : AppCompatActivity() {

    private val viewModel: PlotViewModel by viewModels()
    private var _binding: ActivityPlotBinding? = null
    private val binding get() = _binding!!

    private lateinit var plotAdapter: FeaturedPropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Plot List"

        // Initialize plotAdapter for plot items
        plotAdapter = FeaturedPropertyAdapter(emptyList()) { plotItem ->
            // Handle plot item click here
        }

        binding.rvPlot.apply {
            layoutManager = LinearLayoutManager(this@PlotActivity)
            adapter = plotAdapter
        }

        viewModel.plotList.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    plotAdapter.updateProperties(state.data)
                }

                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state for plot items
                }
            }
        }

        viewModel.loadPlots() // Load plot items when activity is created
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
