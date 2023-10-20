package com.piyal.tmproperty.ui.featuredlist_activity

import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.repository.PropertyRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FeaturedListViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _featuredList = MutableLiveData<UiState<List<Property>>>()
    val featuredList: LiveData<UiState<List<Property>>> get() = _featuredList

    fun loadFeaturedProperties() {
        viewModelScope.launch {
            _featuredList.value = UiState.Loading
            _featuredList.value = propertyRepository.getProperties()
        }
    }
}
