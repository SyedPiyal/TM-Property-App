package com.piyal.tmproperty.repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.piyal.tmproperty.R
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.data.PropertyData
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class PropertyService(private val firestore: FirebaseFirestore) {

    suspend fun getProperties(): List<Property> {
        return getPropertiesFromCollection("Properties", emptyList())
    }

    suspend fun getFeaturedProperties(): List<Property> {
        return getPropertiesFromCollection(
            "Properties",
            listOf("project", "New Project", "new project", "Project", "New project")
        )
    }

    private suspend fun getPropertiesFromCollection(
        collectionName: String,
        types: List<String>
    ): List<Property> {
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addProperty(propertyData: PropertyData, selectedImages: List<Uri>) {
        // Convert propertyData and selectedImages to the appropriate format for Firebase
        // Example: Convert propertyData to HashMap, upload images to Firebase Storage,
        // add download URLs to the propertyData HashMap, and then add the HashMap to Firestore.

        // For example purposes, let's assume you have a function to upload images and get download URLs:
        val downloadUrls = uploadImagesAndGetUrls(selectedImages)

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
        val formattedDateTime = currentDateTime.format(formatter)

        val propertyMap = hashMapOf(
            "type" to propertyData.type,
            "purpose" to propertyData.purpose,
            "price" to propertyData.price,
            "bed" to propertyData.bed,
            "bath" to propertyData.bath,
            "square" to propertyData.square,
            "location" to propertyData.location,
            "title" to propertyData.title,
            "description" to propertyData.description,
            "contact" to propertyData.contact,
            "imageUrls" to downloadUrls,
            "postedDateTime" to formattedDateTime
        )


        // Add property data to Firestore
        firestore.collection("Properties")
            .add(propertyMap)
            .await()
    }

    private suspend fun uploadImagesAndGetUrls(images: List<Uri>): List<String> {
        // Upload images to Firebase Storage and return their download URLs
        // Implement logic to upload images and get download URLs
        // For example:
        val downloadUrls = mutableListOf<String>()
        for (imageUri in images) {
            // Upload image to Firebase Storage and get its download URL
            // Example code (replace with actual upload logic):
            val storageReference =
                FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
            storageReference.putFile(imageUri).await()
            val imageUrl = storageReference.downloadUrl.await().toString()

            // Add download URL to the list
            downloadUrls.add(imageUrl)
        }
        return downloadUrls
    }


    suspend fun getFavoritePropertiesFromFirestore(userId: String): List<Property> {
        val favoritePropertiesList = mutableListOf<Property>()

        try {
            val querySnapshot = firestore.collection("FavoriteList")
                .document(userId)
                .collection("Favorites")
                .get()
                .await()

            for (document in querySnapshot.documents) {
                // Retrieve document fields
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
                    favoritePropertiesList.add(property)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }

        return favoritePropertiesList
    }


        suspend fun getPropertiesForUser(userId: String): List<Property> {
            val propertiesList = mutableListOf<Property>()

            try {
                val querySnapshot = firestore.collection("properties")
                    .whereEqualTo("userId", userId) // Filter properties by userId
                    .get()
                    .await()

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

