package com.piyal.tmproperty.repository


import android.net.Uri
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
    /*suspend fun getPropertyDetails(postId: String): UiState<Property> {
        return try {
            val propertyDetails = propertyService.getPropertyDetails(postId)
            UiState.Success(propertyDetails)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }*/


    suspend fun getFeaturedProperties(types: List<String>): UiState<List<Property>> {
        return try {
            val featuredProperties = propertyService.getFeaturedProperties(types)
            UiState.Success(featuredProperties)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }
    suspend fun addProperty(propertyData: PropertyData): UiState<Boolean> {
        return try {
            propertyService.addProperty(propertyData)
            UiState.Success(true)
        } catch (e: Exception) {
            UiState.Failure(e.message ?: "Failed to add property")
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

     fun getImageSliderData(): UiState<List<SlideModel>> {
        return try {
            val sliderData = propertyService.getImageSliderData()
            UiState.Success(sliderData)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }

    /*suspend fun getPostData(postId: String): PropertyData {
        return propertyService.getPostData(postId)
    }*/

    /*suspend fun updatePost(
        postId: String,
        type: String,
        purpose: String,
        price: String,
        bed: String,
        bath: String,
        square: String,
        location: String,
        title: String,
        description: String,
        contact: String,
        imageUris: List<Uri>
    ): UiState<Boolean> {
        return try {
            propertyService.updatePost(postId, type, purpose, price, bed, bath, square, location, title, description, contact, imageUris)
            UiState.Success(true)
        } catch (e: Exception) {
            UiState.Failure(e.message ?: "Failed to update property")
        }
    }*/



   /* suspend fun deleteProperty(postId: String): UiState<Unit> {
        return try {
            // Implement logic to delete property (e.g., call a delete API)
            propertyService.deleteProperty(postId)
            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }*/

    /*suspend fun checkIfFavorited(postId: String): UiState<Boolean> {
        return try {
            val isFavorited = propertyService.checkIfFavorited(postId)
            UiState.Success(isFavorited)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }*/
    /*suspend fun checkIfFavorited(postId: String): Boolean {
        return try {
            val isFavorited = propertyService.checkIfFavorited(postId)
            isFavorited
        } catch (e: Exception) {
            throw e
        }
    }*/


    /*suspend fun toggleFavorite(postId: String): UiState<Boolean> {
        return try {
            val newFavoriteStatus = propertyService.toggleFavorite(postId)
            UiState.Success(newFavoriteStatus)
        } catch (e: Exception) {
            UiState.Failure(e.message)
        }
    }*/


    //new
    suspend fun getPropertyDetails(postId: String): Property? {
        return propertyService.getPropertyDetails(postId)
    }

    suspend fun checkIfFavorited(currentUserId: String, postId: String): Boolean {
        return propertyService.checkIfFavorited(currentUserId, postId)
    }

    suspend fun toggleFavorite(currentUserId: String, postId: String): Boolean {
        return propertyService.toggleFavorite(currentUserId, postId)
    }

    suspend fun deleteProperty(postId: String): Boolean {
        return propertyService.deleteProperty(postId)
    }
}
