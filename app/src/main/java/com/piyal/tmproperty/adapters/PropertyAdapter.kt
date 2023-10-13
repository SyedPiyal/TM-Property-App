package com.piyal.tmproperty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        private val addressTextView: TextView = itemView.findViewById(R.id.tvFlatAddress)
        private val priceTextView: TextView = itemView.findViewById(R.id.tvFlatPrice)

        fun bind(property: Property) {

            addressTextView.text = property.address
            priceTextView.text = property.price

            itemView.setOnClickListener { itemClickListener.invoke(property) }
        }
    }
}
