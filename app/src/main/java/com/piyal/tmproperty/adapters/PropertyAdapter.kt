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

class PropertyAdapter(
    private var properties: List<Property>,
    private val itemClickListener: (Property) -> Unit
) : RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_view, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int = properties.size

    fun updateProperties(newProperties: List<Property>) {
        properties = newProperties
        notifyDataSetChanged()
    }

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listImg: ImageView = itemView.findViewById(R.id.ivFlatImg)
        private val bedImage: ImageView = itemView.findViewById(R.id.ivBed)
        private val bathImage: ImageView = itemView.findViewById(R.id.ivBath)
        private val roomImage: ImageView = itemView.findViewById(R.id.ivRoom)

        private val tvBed: TextView = itemView.findViewById(R.id.tvBed)
        private val tvBedNumber: TextView = itemView.findViewById(R.id.tvBedNumber)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvFlatAddress)
        private val tvBath: TextView = itemView.findViewById(R.id.tvBath)
        private val tvBathNumber: TextView = itemView.findViewById(R.id.tvBathNumber)
        private val tvRoom: TextView = itemView.findViewById(R.id.tvRoom)
        private val tvSquareNumber: TextView = itemView.findViewById(R.id.tvSquareNumber)
        private val tvTaka: TextView = itemView.findViewById(R.id.tvFlatTk)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvFlatPrice)
        private val tvItemTyp: TextView = itemView.findViewById(R.id.tvItemType)

        fun bind(property: Property) {
            tvAddress.text = property.addressitm
            tvPrice.text = property.tvPrice
            tvBed.text = property.titleBed
            tvBedNumber.text = property.titleBedNumber
            tvBath.text = property.titleBath
            tvBathNumber.text = property.titleBathNumber
            tvRoom.text = property.titleSquare
            tvSquareNumber.text = property.titleSquareNumber
            tvTaka.text = property.taka
            tvItemTyp.text = property.itemType

            // Set your image resources here
            bedImage.setImageResource(property.imgBed)
            bathImage.setImageResource(property.imgBath)
            roomImage.setImageResource(property.imgSqure)
            // Load images from URLs into listImg using your preferred image loading library (Glide, Picasso, etc.)
            // For example, using Glide:
            // Glide.with(itemView.context).load(property.imageUrls.firstOrNull()).into(listImg)
            // Load the first image URL into listImg using Glide
            if (property.imageUrls.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(property.imageUrls[0]) // Load the first image URL from the list
                    //.placeholder(R.drawable.placeholder) // Placeholder image while loading (optional)
                    .error(R.drawable.error_image) // Error image if loading fails (optional)
                    .into(listImg)
            } else {
                // Handle the case where there are no image URLs in the list
                // You can set a default image or hide the ImageView, etc.
                listImg.setImageResource(R.drawable.pic1)
            }

            // Set click listener
            itemView.setOnClickListener { itemClickListener.invoke(property) }
        }

    }
}
