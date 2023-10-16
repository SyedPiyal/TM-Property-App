package com.piyal.tmproperty.ui.favorites

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
class FavoritesViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _favoritesState = MutableLiveData<UiState<List<Property>>>()
    val favoritesState: LiveData<UiState<List<Property>>> get() = _favoritesState

    fun loadFavoriteProperties(userId: String) {
        viewModelScope.launch {
            _favoritesState.value = UiState.Loading
            _favoritesState.value = propertyRepository.getFavoriteProperties(userId)
        }
    }
}
