package com.piyal.tmproperty.ui.addproperty

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
class AddPropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _propertyAdded = MutableLiveData<UiState<Boolean>>()
    val propertyAdded: LiveData<UiState<Boolean>> get() = _propertyAdded

    private val _selectedImages = MutableLiveData<List<Uri>>()
    val selectedImages: LiveData<List<Uri>> get() = _selectedImages

    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean> get() = _permissionGranted

    fun handleImagePicking(fragment: Fragment) {
        val requestPermissionLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                _permissionGranted.value = isGranted
                if (isGranted) {
                    openImagePicker(fragment)
                }
            }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                // Permission already granted, open image picker
                openImagePicker(fragment)
            }
            else -> {
                // Request permission to read external storage
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openImagePicker(fragment: Fragment) {
        val imagePickerLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                if (uri != null) {
                    val currentImages = _selectedImages.value.orEmpty().toMutableList()
                    currentImages.add(uri)
                    _selectedImages.value = currentImages
                }
            }

        fragment.activity?.let {
            imagePickerLauncher.launch("image/*")
        }
    }

    fun addProperty(propertyData: PropertyData) {
        viewModelScope.launch {
            _propertyAdded.value = UiState.Loading
            val result = propertyRepository.addProperty(propertyData)
            _propertyAdded.value = result
        }
    }
}
