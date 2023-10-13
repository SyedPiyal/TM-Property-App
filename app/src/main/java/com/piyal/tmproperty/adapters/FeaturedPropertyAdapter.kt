package com.piyal.tmproperty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.piyal.tmproperty.data.Property

class FeaturedPropertyAdapter(
    private var featuredProperties: List<Property>,
    private val itemClickListener: (Property) -> Unit
) : RecyclerView.Adapter<FeaturedPropertyAdapter.FeaturedPropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedPropertyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemFeaturedPropertyBinding.inflate(inflater, parent, false)
        return FeaturedPropertyViewHolder(binding)
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

    inner class FeaturedPropertyViewHolder(private val binding: ListItemFeaturedPropertyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) {
            binding.property = property
            binding.root.setOnClickListener { itemClickListener.invoke(property) }
            binding.executePendingBindings()
        }
    }
}
