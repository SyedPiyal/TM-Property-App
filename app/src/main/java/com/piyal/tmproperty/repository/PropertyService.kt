package com.piyal.tmproperty.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.piyal.tmproperty.R
import com.piyal.tmproperty.data.Property
import kotlinx.coroutines.tasks.await

class PropertyService(private val firestore: FirebaseFirestore) {

    suspend fun getProperties(): List<Property> {
        return getPropertiesFromCollection("Properties", emptyList())
    }

    suspend fun getFeaturedProperties(): List<Property> {
        return getPropertiesFromCollection("Properties", listOf("project", "New Project", "new project", "Project", "New project"))
    }

    private suspend fun getPropertiesFromCollection(collectionName: String, types: List<String>): List<Property> {
        val propertiesList = mutableListOf<Property>()

        try {
            val querySnapshot = firestore.collection(collectionName)
                .whereIn("type", types)
                .get().await()

            for (document in querySnapshot.documents) {
                val imageUrlList = document.get("imageUrls") as? List<String>
                val imageUrl = imageUrlList?.firstOrNull()
                val titleBed = "Bed:"
                val titleBedNumber = document.getString("bed") ?: ""
                val titleBath = "Bath:"
                val titleBathNumber = document.getString("bath") ?: ""
                val titleSquare = "Sqft:"
                val titleSquareNumber = document.getString("square") ?: ""
                val addressitm = document.getString("location") ?: ""
                val itemType = document.getString("type") ?: ""
                val taka = "BDT"
                val tvPrice = document.getString("price") ?: ""
                val imgBed = R.drawable.ic_bedd
                val imgBath = R.drawable.ic_bath
                val imgSqure = R.drawable.ic_square
                val userId = document.getString("userId") ?: ""
                val postId = document.id
                val titleitm = document.getString("title") ?: ""
                val purpose = document.getString("purpose") ?: ""
                val description = document.getString("description") ?: ""
                val contact = document.getString("contact") ?: ""
                val postedDateTime = document.getString("postedDateTime") ?: ""

                imageUrl?.let {
                    val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(it)
                    val property = Property(
                        titleBed, titleBedNumber, titleBath, titleBathNumber, titleSquare,
                        titleSquareNumber, addressitm, itemType, taka, tvPrice, storageRef,
                        imgBed, imgBath, imgSqure, userId, postId, titleitm, purpose,
                        description, contact, postedDateTime, imageUrlList.orEmpty()
                    )
                    propertiesList.add(property)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }

        return propertiesList
    }
}

