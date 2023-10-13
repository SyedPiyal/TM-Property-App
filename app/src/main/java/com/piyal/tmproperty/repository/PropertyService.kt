package com.piyal.tmproperty.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.piyal.tmproperty.data.Property
import kotlinx.coroutines.tasks.await

class PropertyService(private val firestore: FirebaseFirestore) {

    suspend fun getProperties(): List<Property> {
        val propertiesList = mutableListOf<Property>()

        try {
            val querySnapshot = firestore.collection("properties").get().await()

            for (document in querySnapshot.documents) {
                val id = document.id
                val title = document.getString("title") ?: ""
                val address = document.getString("address") ?: ""
                val price = document.getString("price") ?: ""

                // Create a Property object and add it to the list
                val property = Property(id, title, address, price)
                propertiesList.add(property)
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }

        return propertiesList
    }

    suspend fun getFeaturedProperties(): List<Property> {
        val featuredPropertiesList = mutableListOf<Property>()

        try {
            val querySnapshot = firestore.collection("featured_properties").get().await()

            for (document in querySnapshot.documents) {
                val id = document.id
                val title = document.getString("title") ?: ""
                val address = document.getString("address") ?: ""
                val price = document.getString("price") ?: ""

                // Create a Property object and add it to the list of featured properties
                val property = Property(id, title, address, price)
                featuredPropertiesList.add(property)
            }
        } catch (e: Exception) {
            // Handle exceptions here
            e.printStackTrace()
        }

        return featuredPropertiesList
    }
}
