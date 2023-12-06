package com.piyal.tmproperty.ui.favorites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.adapters.PropertyAdapter
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.databinding.FragmentFavoritesBinding
import com.piyal.tmproperty.util.PropertyNavigationUtil
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val propertyAdapter = PropertyAdapter(emptyList()) { property ->
        // Handle favorite property item click here
        openPropertyDetails(property)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvFavoriteList
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = propertyAdapter


        

        // Observe the favorites state and update the UI accordingly
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    propertyAdapter.updateProperties(state.data)
                }

                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state, show error message, etc.
                }
            }
        }

        // Load favorite properties when the fragment is created
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        currentUserId?.let {
            viewModel.loadFavoriteProperties(it)
        }

        return root
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

        PropertyNavigationUtil.openPropertyDetails(requireContext(), property)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
