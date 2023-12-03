package com.piyal.tmproperty.repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.piyal.tmproperty.R
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.data.PropertyData

import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class PropertyService(private val firestore: FirebaseFirestore, private val storage: FirebaseStorage) {

   /* suspend fun getProperties(): List<Property> {
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
    }*/



       /* suspend fun getProperties(): UiState<List<Property>> {
            return try {
                val querySnapshot = firestore.collection("Properties")
                    .get().await()

                val propertiesList = mutableListOf<Property>()

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

                UiState.Success(propertiesList)
            } catch (e: Exception) {
                UiState.Failure(e.message)
            }
        }

        suspend fun getFeaturedProperties(): UiState<List<Property>> {
            return try {
                val querySnapshot = firestore.collection("Properties")
                    .whereIn("type", listOf("project", "New Project", "new project", "Project", "New project"))
                    .get().await()

                val featuredPropertiesList = mutableListOf<Property>()

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

                    featuredPropertiesList.add(property)
                }
                }

                UiState.Success(featuredPropertiesList)
            } catch (e: Exception) {
                UiState.Failure(e.message)
            }
        }*/





        suspend fun getProperties(): List<Property> {
            return try {
                val querySnapshot = firestore.collection("Properties")
                    .get().await()

                val propertiesList = mutableListOf<Property>()

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
                            description, contact, postedDateTime, imageUrlList
                        )
                        propertiesList.add(property)
                    }
                }

                propertiesList
            } catch (e: Exception) {
                // Handle exceptions here
                e.printStackTrace()
                emptyList() // Return an empty list in case of an error
            }
        }

        suspend fun getFeaturedProperties(types: List<String>): List<Property> {
            return try {
                val querySnapshot = firestore.collection("Properties")
                    .whereIn("type",types)
                    .get().await()

                val featuredPropertiesList = mutableListOf<Property>()

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
                        featuredPropertiesList.add(property)
                    }
                }

                featuredPropertiesList
            } catch (e: Exception) {
                // Handle exceptions here
                e.printStackTrace()
                emptyList() // Return an empty list in case of an error
            }
        }



        suspend fun addProperty(propertyData: PropertyData) {
            // Convert PropertyData to a HashMap
            val data = hashMapOf(
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
                "userId" to propertyData.userId,
                "postedDateTime" to propertyData.postedDateTime,
                "imageUris" to propertyData.imageUris
            )

            // Add data to Firestore
            val documentReference = firestore.collection("Properties").add(data).await()

            // Upload images to Firebase Storage
            propertyData.imageUris.forEach { imageUrl ->
                val imageUri = Uri.parse(imageUrl)
                val storageReference = storage.reference.child("uploads/${UUID.randomUUID()}")
                val uploadTask = storageReference.putFile(imageUri)

                //val uploadTask = storageReference.putFile(imageUri).await()
                /*if (uploadTask.isSuccessful) {
                    val downloadUrl = storageReference.downloadUrl.await().toString()
                    // Update the image URLs in the Firestore document
                    firestore.collection("Properties")
                        .document(documentReference.id)
                        .update("imageUris", FieldValue.arrayUnion(downloadUrl))
                        .await()
                } else {
                    // Handle image upload failure
                    throw Exception("Failed to upload images")
                }*/
               /* try {
                    // Await the upload task completion and get the download URL
                    val downloadUrl = storageReference.downloadUrl.await().toString()

                    // Update the image URLs in the Firestore document
                    firestore.collection("Properties")
                        .document(documentReference.id)
                        .update("imageUris", FieldValue.arrayUnion(downloadUrl))
                        .await()
                } catch (e: Exception) {
                    // Handle image upload failure
                    throw Exception("Failed to upload images: ${e.message}")
                }*/

                // Add a listener to the upload task
                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // If upload is successful, get the download URL
                        storageReference.downloadUrl.addOnSuccessListener { downloadUrl ->
                            // Update the image URLs in the Firestore document
                            firestore.collection("Properties")
                                .document(documentReference.id)
                                .update("imageUris", FieldValue.arrayUnion(downloadUrl.toString()))
                                .addOnSuccessListener {
                                    // Image URL updated successfully
                                }
                                .addOnFailureListener { e ->
                                    // Handle failure in updating image URL
                                }
                        }
                    } else {
                        // Handle failure in uploading the image
                        // You can access the exception using task.exception
                    }
                }
            }
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
                        description, contact, postedDateTime, imageUrlList
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
                            description, contact, postedDateTime, imageUrlList
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


     fun getImageSliderData(): List<SlideModel> {
        // Hardcoded data for the image slider
        return listOf(
            SlideModel(R.drawable.forest_house, "Property in Dhaka", ScaleTypes.FIT),
            SlideModel(R.drawable.home_ban, "Property in Barishal", ScaleTypes.FIT),
            SlideModel(R.drawable.reso, "Property in Chattogram", ScaleTypes.FIT),
            SlideModel(R.drawable.image, "Property in Khulna", ScaleTypes.FIT),
            SlideModel(R.drawable.machan, "Property in Rajshahi", ScaleTypes.FIT)
        )
    }

    suspend fun getPostData(postId: String): PropertyData {
        return try {
            val documentSnapshot = firestore.collection("Properties").document(postId).get().await()

            if (documentSnapshot.exists()) {
                val type = documentSnapshot.getString("type") ?: ""
                val purpose = documentSnapshot.getString("purpose") ?: ""
                val price = documentSnapshot.getString("price") ?: ""
                val bed = documentSnapshot.getString("bed") ?: ""
                val bath = documentSnapshot.getString("bath") ?: ""
                val square = documentSnapshot.getString("square") ?: ""
                val location = documentSnapshot.getString("location") ?: ""
                val title = documentSnapshot.getString("title") ?: ""
                val description = documentSnapshot.getString("description") ?: ""
                val contact = documentSnapshot.getString("contact") ?: ""

                // Retrieve and parse other properties as needed

                PropertyData(type, purpose, price, bed, bath, square, location, title, description, contact, postId,postedDateTime,
                    emptyList())
            } else {
                // Handle the case when the document does not exist
                throw Exception("Property not found")
            }
        } catch (e: Exception) {
            // Handle exceptions here
            throw Exception("Failed to retrieve property data: ${e.message}")
        }
    }

}

