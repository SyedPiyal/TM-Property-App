package com.piyal.tmproperty.ui.update_activity

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyal.tmproperty.data.PropertyData
import com.piyal.tmproperty.repository.PropertyRepository
import com.piyal.tmproperty.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _selectedImages = MutableLiveData<MutableList<Uri>>(mutableListOf())
    val selectedImages: LiveData<MutableList<Uri>> get() = _selectedImages

    private val _postData = MutableLiveData<PropertyData>()
    val postData: LiveData<PropertyData> get() = _postData

    private val _propertyAdded = MutableLiveData<UiState<Boolean>>()
    val propertyAdded: LiveData<UiState<Boolean>> get() = _propertyAdded


    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean> get() = _permissionGranted

    fun retrievePostData(postId: String) {
        viewModelScope.launch {
            _postData.value = propertyRepository.getPostData(postId)
        }
    }

    fun updatePost(postId: String, type: String, purpose: String, price: String, bed: String,
                   bath: String, square: String, location: String, title: String,
                   description: String, contact: String) {
        viewModelScope.launch {
            propertyRepository.updatePost(postId, type, purpose, price, bed, bath, square, location, title, description, contact, _selectedImages.value.orEmpty())
        }
    }



    fun handleImagePicking(fragment: UpdateActivity) {
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
}
