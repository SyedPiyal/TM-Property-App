package com.piyal.tmproperty.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piyal.tmproperty.R

class ImageAdapter(private val images: MutableList<Uri>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView_re)
        val delete: ImageView = view.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val imageUri = images[position]
        val imageUri = images[holder.adapterPosition]

        val context = holder.itemView.context
        // Load the image from the URI into the ImageView
        Glide.with(context)
            .load(imageUri)
            .into(holder.imageView)

        holder.imageView.setImageURI(imageUri)

        holder.delete.setOnClickListener {
            // Remove the image URI from the list

            //images.removeAt(position)
            // Notify the adapter about the removed item
            //notifyItemRemoved(position)

            // Notify the adapter that the dataset has changed
            //notifyDataSetChanged()
            // Notify the adapter about the removed item

            //notifyItemRemoved(position)
            //notifyItemRangeChanged(position, images.size)

            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                images.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, itemCount)
            }
        }

    }

    override fun getItemCount(): Int {
        return images.size
    }

    // Ensure each item has a unique ID by returning its position as an ID
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newImages: List<Uri>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()

    }
    // Add this method to expose the images list

    fun getSelectedImages(): List<Uri> {
        return images
    }


}
