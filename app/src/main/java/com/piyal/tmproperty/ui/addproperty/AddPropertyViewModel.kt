package com.piyal.tmproperty.ui.addproperty

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.data.PropertyData
import com.piyal.tmproperty.repository.PropertyRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _propertyState = MutableLiveData<UiState<Unit>>()
    val propertyState: LiveData<UiState<Unit>> get() = _propertyState

    fun addProperty(propertyData: PropertyData, selectedImages: List<Uri>) {
        viewModelScope.launch {
            _propertyState.value = UiState.Loading
            val result = propertyRepository.addProperty(propertyData, selectedImages)
            _propertyState.value = result
        }
    }
}
