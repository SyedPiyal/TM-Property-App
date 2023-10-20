package com.piyal.tmproperty.ui.plot_activity

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
class PlotViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _plotList = MutableLiveData<UiState<List<Property>>>()
    val plotList: LiveData<UiState<List<Property>>> get() = _plotList

    fun loadPlots() {
        viewModelScope.launch {
            _plotList.value = UiState.Loading
            _plotList.value = propertyRepository.getFeaturedProperties(listOf("plot", "Plot", "New Plot","New plot"))
        }
    }
}
