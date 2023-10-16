package com.piyal.tmproperty.repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.data.PropertyData
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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addProperty(propertyData: PropertyData, selectedImages: List<Uri>): UiState<Unit> {
        return try {
            propertyService.addProperty(propertyData, selectedImages)
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
    suspend fun getFavoriteProperties(userId: String): UiState<List<Property>> {
        return try {
            val properties = propertyService.getFavoritePropertiesFromFirestore(userId)
            UiState.Success(properties)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
    suspend fun getPropertiesForUser(userId: String): UiState<List<Property>> {
        return try {
            val properties = propertyService.getPropertiesForUser(userId)
            UiState.Success(properties)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

}
