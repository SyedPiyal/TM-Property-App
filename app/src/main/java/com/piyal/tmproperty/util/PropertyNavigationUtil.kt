package com.piyal.tmproperty.util

import android.content.Context
import android.content.Intent
import com.piyal.tmproperty.data.Property
import com.piyal.tmproperty.ui.details_activity.DetailsActivity

object PropertyNavigationUtil {

    fun openPropertyDetails(context: Context, property: Property) {
        val intent = Intent(context, DetailsActivity::class.java).apply {
            // Pass property details to DetailsActivity
            putExtra("bed", property.titleBedNumber)
            putExtra("bath", property.titleBathNumber)
            putExtra("square", property.titleSquareNumber)
            putExtra("address", property.addressitm)
            putExtra("type", property.itemType)
            putExtra("price", property.tvPrice)
            putExtra("userId", property.userId)
            putExtra("postId", property.postId)
            putExtra("imageUrls", ArrayList(property.imageUrls))
            putExtra("title", property.titleitm)
            putExtra("purpose", property.purpose)
            putExtra("description", property.description)
            putExtra("contact", property.contact)
            putExtra("postedDateTime", property.postedDateTime)
        }
        context.startActivity(intent)
    }
}
