package com.piyal.tmproperty.ui.apartment_activity

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
class ApartmentViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _apartmentList = MutableLiveData<UiState<List<Property>>>()
    val apartmentList: LiveData<UiState<List<Property>>> get() = _apartmentList

    fun loadApartments() {
        viewModelScope.launch {
            _apartmentList.value = UiState.Loading
            _apartmentList.value = propertyRepository.getFeaturedProperties(listOf("apartment", "Apartment", "New Apartment", "New apartment") )
        }
    }
}
