package com.piyal.tmproperty.ui.mylist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.piyal.tmproperty.adapters.PropertyAdapter
import com.piyal.tmproperty.databinding.FragmentMyListBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyListFragment : Fragment() {

    private val viewModel: MyListViewModel by viewModels()
    private var _binding: FragmentMyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var propertyAdapter: PropertyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        propertyAdapter = PropertyAdapter(emptyList()) { property ->
            // Handle property item click here
            // You can navigate to the details screen or perform any action here
        }

        binding.rvMylist.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvMylist.adapter = propertyAdapter

        viewModel.myListState.observe(viewLifecycleOwner) { state ->
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

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        currentUser?.let { userId ->
            viewModel.loadMyList(userId)
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
