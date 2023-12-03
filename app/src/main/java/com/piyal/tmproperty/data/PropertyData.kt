package com.piyal.tmproperty.data

data class PropertyData(
    val type: String,
    val purpose: String,
    val price: String,
    val bed: String,
    val bath: String,
    val square: String,
    val location: String,
    val title: String,
    val description: String,
    val contact: String,
    val userId: String,
    val postedDateTime: String,
    val imageUris: List<String>
)

