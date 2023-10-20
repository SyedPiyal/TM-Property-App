package com.piyal.tmproperty.ui.projectlist_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.piyal.tmproperty.adapters.FeaturedPropertyAdapter
import com.piyal.tmproperty.databinding.ActivityProjectListBinding
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectListActivity : AppCompatActivity() {

    private val viewModel: ProjectViewModel by viewModels()
    private var _binding: ActivityProjectListBinding? = null
    private val binding get() = _binding!!

    private lateinit var projectAdapter: FeaturedPropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProjectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Project List"

        // Initialize projectAdapter for project properties
        projectAdapter = FeaturedPropertyAdapter(emptyList()) { property ->
            // Handle project property item click here
        }

        binding.rvNewProject.apply {
            layoutManager = LinearLayoutManager(this@ProjectListActivity, RecyclerView.VERTICAL, false)
            adapter = projectAdapter
        }

        viewModel.projectList.observe(this) { state ->
            when (state) {
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    hideLoading()
                    projectAdapter.updateProperties(state.data)
                }
                is UiState.Failure -> {
                    hideLoading()
                    // Handle error state for project properties
                }
            }
        }

        viewModel.loadProjects() // Load project properties when activity is created
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
