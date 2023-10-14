package com.piyal.tmproperty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

        fun bind(property: Property) {
            titleTextView.text = property.titleitm
            addressTextView.text = property.addressitm
            priceTextView.text = property.tvPrice

            itemView.setOnClickListener { itemClickListener.invoke(property) }
        }
    }
}
