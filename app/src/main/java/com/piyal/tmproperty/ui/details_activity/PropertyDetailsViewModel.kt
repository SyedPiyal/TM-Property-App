package com.piyal.tmproperty.ui.details_activity

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

/*
@HiltViewModel
class PropertyDetailsViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _propertyDetails = MutableLiveData<UiState<Property>>()
    val propertyDetails: LiveData<UiState<Property>> get() = _propertyDetails

    private val _deletePropertyState = MutableLiveData<UiState<Unit>>()
    val deletePropertyState: LiveData<UiState<Unit>> get() = _deletePropertyState

    private val _toggleFavoriteState = MutableLiveData<UiState<Boolean>>()
    val toggleFavoriteState: LiveData<UiState<Boolean>> get() = _toggleFavoriteState

    fun loadPropertyDetails(postId: String) {
        viewModelScope.launch {
            _propertyDetails.value = UiState.Loading
            _propertyDetails.value = propertyRepository.getPropertyDetails(postId)
        }
    }

    fun deleteProperty(postId: String) {
        viewModelScope.launch {
            _deletePropertyState.value = UiState.Loading
            _deletePropertyState.value = propertyRepository.deleteProperty(postId)
        }
    }

 */
/*fun checkIfFavorited(postId: String) {
        viewModelScope.launch {
            _toggleFavoriteState.value = UiState.Loading
            _toggleFavoriteState.value = propertyRepository.checkIfFavorited(postId)
        }
    }
*//*


    fun checkIfFavorited(postId: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            _toggleFavoriteState.value = UiState.Loading
            val isFavorited = propertyRepository.checkIfFavorited(postId)
            callback(isFavorited)
        }
    }






    fun toggleFavorite(postId: String) {
        viewModelScope.launch {
            _toggleFavoriteState.value = UiState.Loading
            _toggleFavoriteState.value = propertyRepository.toggleFavorite(postId)
        }
    }
}
*/


@HiltViewModel
class PropertyDetailsViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _propertyDetails = MutableLiveData<UiState<Property>>()
    val propertyDetails: LiveData<UiState<Property>> get() = _propertyDetails

    private val _deletePropertyState = MutableLiveData<UiState<Unit>>()
    val deletePropertyState: LiveData<UiState<Unit>> get() = _deletePropertyState

    private val _toggleFavoriteState = MutableLiveData<UiState<Boolean>>()
    val toggleFavoriteState: LiveData<UiState<Boolean>> get() = _toggleFavoriteState

    private val _checkIfFavoritedState = MutableLiveData<UiState<Boolean>>()
    val checkIfFavoritedState: LiveData<UiState<Boolean>> get() = _checkIfFavoritedState



    fun getPropertyDetails(postId: String) {
        viewModelScope.launch {
            _propertyDetails.value = UiState.Loading
            try {
                val property = propertyRepository.getPropertyDetails(postId)
                if (property != null) {
                    _propertyDetails.value = UiState.Success(property)
                } else {
                    _propertyDetails.value = UiState.Failure("Property not found")
                }
            } catch (e: Exception) {
                _propertyDetails.value = UiState.Failure(e.message)
            }
        }
    }


    fun deleteProperty(postId: String) {
        viewModelScope.launch {
            _deletePropertyState.value = UiState.Loading
            try {
                propertyRepository.deleteProperty(postId)
                _deletePropertyState.value = UiState.Success(Unit)
            } catch (e: Exception) {
                _deletePropertyState.value = UiState.Failure(e.message)
            }
        }
    }

    fun toggleFavorite(currentUserId: String, postId: String) {
        viewModelScope.launch {
            _toggleFavoriteState.value = UiState.Loading
            try {
                val isFavorite = propertyRepository.toggleFavorite(currentUserId, postId)
                _toggleFavoriteState.value = UiState.Success(isFavorite)
            } catch (e: Exception) {
                _toggleFavoriteState.value = UiState.Failure(e.message)
            }
        }
    }

    fun checkIfFavorited(currentUserId: String, postId: String) {
        viewModelScope.launch {
            _checkIfFavoritedState.value = UiState.Loading
            try {
                val isFavorited = propertyRepository.checkIfFavorited(currentUserId, postId)
                _checkIfFavoritedState.value = UiState.Success(isFavorited)
            } catch (e: Exception) {
                _checkIfFavoritedState.value = UiState.Failure(e.message)
            }
        }
    }
}

