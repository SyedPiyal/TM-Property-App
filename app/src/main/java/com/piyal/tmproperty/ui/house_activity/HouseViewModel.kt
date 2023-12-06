package com.piyal.tmproperty.ui.house_activity

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
class HouseViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _houseList = MutableLiveData<UiState<List<Property>>>()
    val houseList: LiveData<UiState<List<Property>>> get() = _houseList

    fun loadHouses() {
        viewModelScope.launch {
            _houseList.value = UiState.Loading
            _houseList.value = propertyRepository.getFeaturedProperties(listOf("house", "House", "New House", "New house","Home","home","HOME"))
        }
    }
}
