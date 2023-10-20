package com.piyal.tmproperty.ui.projectlist_activity

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
class ProjectViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    private val _projectList = MutableLiveData<UiState<List<Property>>>()
    val projectList: LiveData<UiState<List<Property>>> get() = _projectList

    fun loadProjects() {
        viewModelScope.launch {
            _projectList.value = UiState.Loading
            _projectList.value = propertyRepository.getFeaturedProperties(listOf("project", "Project", "New Project", "New project"))
        }
    }
}
