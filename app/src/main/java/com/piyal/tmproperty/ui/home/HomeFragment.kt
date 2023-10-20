package com.piyal.tmproperty.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.adapters.PropertyAdapter
import com.piyal.tmproperty.databinding.FragmentHomeBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var featuredPropertyAdapter: FeaturedPropertyAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val imageSlider: ImageSlider = binding.imageSlider

        // Initialize propertyAdapter for regular properties
        propertyAdapter = PropertyAdapter(emptyList()) { property ->
            // Handle property item click here
        }

        // Initialize featuredPropertyAdapter for featured properties
        featuredPropertyAdapter = FeaturedPropertyAdapter(emptyList()) { property ->
            // Handle featured property item click here
        }

        binding.rvFeatured.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = propertyAdapter
        }

        binding.rvNew.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = featuredPropertyAdapter
        }

        viewModel.propertyState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    propertyAdapter.updateProperties(state.data)
                }

                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state for regular properties
                }
            }
        }

        viewModel.featuredPropertyState.observe(viewLifecycleOwner) { state ->
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



        viewModel.imageSliderState.observe(viewLifecycleOwner) { sliderState ->
            when (sliderState) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    val sliderData = sliderState.data
                    // Update the image slider with the fetched data
                    imageSlider.setImageList(sliderData)
                }
                is UiState.Failure -> hideLoading()
            }
        }

        viewModel.loadImageSliderData()// Load image slider data when fragment is created


        viewModel.loadProperties() // Load regular properties when fragment is created
        viewModel.loadFeaturedProperties() // Load featured properties when fragment is created

        binding.btnHouse.setOnClickListener {
            viewModel.navigateToHouseActivity(requireContext())
        }

        binding.btnApartment.setOnClickListener {
            viewModel.navigateToApartmentActivity(requireContext())
        }

        binding.btnPlot.setOnClickListener {
            viewModel.navigateToPlotActivity(requireContext())
        }

        binding.tvFListSee.setOnClickListener {
            viewModel.navigateToProjectListActivity(requireContext())
        }

        binding.tvFeatured.setOnClickListener {
            viewModel.navigateToFeaturedListActivity(requireContext())
        }

        binding.searchIcon.setOnClickListener {
            val searchCriteria = binding.edSearch.text.toString().trim()
            viewModel.setSearchQuery(searchCriteria)
        }

        binding.edSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchCriteria = binding.edSearch.text.toString().trim()
                viewModel.setSearchQuery(searchCriteria)
                true
            } else {
                false
            }
        }

        // Observe search query changes and navigate to SearchActivity
        viewModel.searchQuery.observe(viewLifecycleOwner) { searchQuery ->
            if (searchQuery.isNotEmpty()) {
                viewModel.navigateToSearchActivity(requireContext(), searchQuery)
            } else {
                Toast.makeText(requireContext(), "Please enter a search term.", Toast.LENGTH_SHORT).show()
            }
        }


        return root
    }

    private fun showLoading() {
        // Show loading UI if needed
    }

    private fun hideLoading() {
        // Hide loading UI if needed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
