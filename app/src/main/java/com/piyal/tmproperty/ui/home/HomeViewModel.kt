package com.piyal.tmproperty.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.repository.PropertyRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _propertyState = MutableLiveData<UiState<List<Property>>>()
    val propertyState: LiveData<UiState<List<Property>>> get() = _propertyState

    private val _featuredPropertyState = MutableLiveData<UiState<List<Property>>>()
    val featuredPropertyState: LiveData<UiState<List<Property>>> get() = _featuredPropertyState

    fun loadProperties() {
        viewModelScope.launch {
            _propertyState.value = UiState.Loading
            _propertyState.value = propertyRepository.getProperties()
        }
    }

    fun loadFeaturedProperties() {
        viewModelScope.launch {
            _featuredPropertyState.value = UiState.Loading
            _featuredPropertyState.value = propertyRepository.getFeaturedProperties()
        }
    }
}
