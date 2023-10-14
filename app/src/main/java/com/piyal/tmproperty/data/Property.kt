package com.piyal.tmproperty.data

import com.google.firebase.storage.StorageReference

data class Property(
    val titleBed: String,
    val titleBedNumber: String,
    val titleBath: String,
    val titleBathNumber: String,
    val titleSquare: String,
    val titleSquareNumber: String,
    val addressitm: String,
    val itemType: String,
    val taka: String,
    val tvPrice: String,
    val imageRef: StorageReference,
    val imgBed: Int,
    val imgBath: Int,
    val imgSqure: Int,

    val userId: String,
    val postId: String,
    //
    val titleitm: String,
    val purpose: String,
    val description: String,
    val contact: String,


    //
    val postedDateTime: String,
    //

    val imageUrls: List<String>, // Add the imageUrls property
)
