package com.piyal.tmproperty.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.models.SlideModel
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.repository.PropertyRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.piyal.tmproperty.ui.apartment_activity.ApartmentActivity
import com.piyal.tmproperty.ui.featuredlist_activity.FeaturedListActivity
import com.piyal.tmproperty.ui.house_activity.HouseActivity
import com.piyal.tmproperty.ui.plot_activity.PlotActivity
import com.piyal.tmproperty.ui.projectlist_activity.ProjectListActivity

@HiltViewModel
class HomeViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _propertyState = MutableLiveData<UiState<List<Property>>>()
    val propertyState: LiveData<UiState<List<Property>>> get() = _propertyState

    private val _featuredPropertyState = MutableLiveData<UiState<List<Property>>>()
    val featuredPropertyState: LiveData<UiState<List<Property>>> get() = _featuredPropertyState

    private val _imageSliderState = MutableLiveData<UiState<List<SlideModel>>>()
    val imageSliderState: LiveData<UiState<List<SlideModel>>> get() = _imageSliderState

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    fun loadProperties() {
        viewModelScope.launch {
            _propertyState.value = UiState.Loading
            _propertyState.value = propertyRepository.getProperties()
        }
    }

    fun loadFeaturedProperties() {
        viewModelScope.launch {
            //val projectTypes = listOf("project", "Project", "New Project", "New project") // Customize the list of plot types as needed
            _featuredPropertyState.value = UiState.Loading
            _featuredPropertyState.value = propertyRepository.getFeaturedProperties(listOf("project", "Project", "New Project", "New project") )
        }
    }
    fun loadImageSliderData() {
        viewModelScope.launch {
            _imageSliderState.value = UiState.Loading
            _imageSliderState.value = propertyRepository.getImageSliderData()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    // Function to handle navigation to specific activities
    fun navigateToHouseActivity(context: Context) {
        context.startActivity(Intent(context, HouseActivity::class.java))
    }

    fun navigateToApartmentActivity(context: Context) {
        context.startActivity(Intent(context, ApartmentActivity::class.java))
    }

    fun navigateToPlotActivity(context: Context) {
        context.startActivity(Intent(context, PlotActivity::class.java))
    }

    fun navigateToProjectListActivity(context: Context) {
        context.startActivity(Intent(context, ProjectListActivity::class.java))
    }

    fun navigateToFeaturedListActivity(context: Context) {
        context.startActivity(Intent(context, FeaturedListActivity::class.java))
    }

    // Function to handle navigation to SearchActivity
    fun navigateToSearchActivity(context: Context, searchQuery: String) {
        /*val trimmedQuery = searchQuery.trim()
        if (trimmedQuery.isNotEmpty()) {
            val intent = Intent(context, SearchActivity::class.java).apply {
                putExtra("searchCriteria", trimmedQuery)
            }
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Please enter a search term.", Toast.LENGTH_SHORT).show()
        }*/
    }
}
