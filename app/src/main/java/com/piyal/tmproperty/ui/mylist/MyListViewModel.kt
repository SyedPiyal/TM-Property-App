package com.piyal.tmproperty.ui.mylist


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
class MyListViewModel @Inject constructor(private val propertyRepository: PropertyRepository) : ViewModel() {

    private val _myListState = MutableLiveData<UiState<List<Property>>>()
    val myListState: LiveData<UiState<List<Property>>> get() = _myListState


    fun loadMyList(userId: String) {
        viewModelScope.launch {
            _myListState.value = UiState.Loading
            try {
                val myList = propertyRepository.getMyList(userId)
                _myListState.value = UiState.Success(myList)
            } catch (e: Exception) {
                _myListState.value = UiState.Failure(e.message)
            }
        }
    }
}
