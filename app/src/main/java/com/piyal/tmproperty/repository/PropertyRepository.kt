package com.piyal.tmproperty.repository

import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.util.UiState
import javax.inject.Inject

class PropertyRepository @Inject constructor(private val propertyService: PropertyService) {

    suspend fun getProperties(): UiState<List<Property>> {
        return try {
            val properties = propertyService.getProperties()
            UiState.Success(properties)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

    suspend fun getFeaturedProperties(): UiState<List<Property>> {
        return try {
            val featuredProperties = propertyService.getFeaturedProperties()
            UiState.Success(featuredProperties)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
}
