package com.piyal.tmproperty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piyal.tmproperty.R
import com.piyal.tmproperty.data.Property

class FeaturedPropertyAdapter(
    private var featuredProperties: List<Property>,
    private val itemClickListener: (Property) -> Unit
) : RecyclerView.Adapter<FeaturedPropertyAdapter.FeaturedPropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedPropertyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return FeaturedPropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedPropertyViewHolder, position: Int) {
        val property = featuredProperties[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int = featuredProperties.size

    fun updateProperties(newProperties: List<Property>) {
        featuredProperties = newProperties
        notifyDataSetChanged()
    }

    inner class FeaturedPropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val addressTextView: TextView = itemView.findViewById(R.id.tvAddresstxt)
        private val priceTextView: TextView = itemView.findViewById(R.id.tvItemFltPrice)
        private val markerImage: ImageView = itemView.findViewById(R.id.imgMarker)
        private val itemviewimage: ImageView = itemView.findViewById(R.id.itemimg)
        private val tvTaka: TextView = itemView.findViewById(R.id.tvBDT)

        fun bind(property: Property) {
            titleTextView.text = property.titleitm
            addressTextView.text = property.addressitm
            priceTextView.text = property.tvPrice
            tvTaka.text = property.taka
            markerImage.setImageResource(property.imgBath)

            if (property.imageUrls.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(property.imageUrls[0]) // Load the first image URL from the list
                    //.placeholder(R.drawable.placeholder) // Placeholder image while loading (optional)
                    .error(R.drawable.error_image) // Error image if loading fails (optional)
                    .into(itemviewimage)
            } else {
                // Handle the case where there are no image URLs in the list
                // You can set a default image or hide the ImageView, etc.
                itemviewimage.setImageResource(R.drawable.pic1)
            }

            itemView.setOnClickListener { itemClickListener.invoke(property) }
        }
    }
}
