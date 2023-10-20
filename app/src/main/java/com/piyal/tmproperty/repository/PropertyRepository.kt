package com.piyal.tmproperty.repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.denzcoskun.imageslider.models.SlideModel
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

    suspend fun getFeaturedProperties(types: List<String>): UiState<List<Property>> {
        return try {
            val featuredProperties = propertyService.getFeaturedProperties(types)
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
    suspend fun getMyList(userId: String): UiState<List<Property>> {
        return try {
            val myList = propertyService.getPropertiesForUser(userId)
            UiState.Success(myList)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

    suspend fun getImageSliderData(): UiState<List<SlideModel>> {
        return try {
            val sliderData = propertyService.getImageSliderData()
            UiState.Success(sliderData)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

}
