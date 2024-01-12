package com.example.customar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customar.R
import com.example.customar.models.Item
import com.example.customar.utils.Constants.BASE_URL

class ItemImagesAdapter(private val images: List<String>) : RecyclerView.Adapter<ItemImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        val url = BASE_URL+ "/"+ imageUrl

        Glide.with(holder.itemView.context)
            .load(url)
            .placeholder(R.drawable.ic_spinner) // Placeholder image
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iteminfoimage)

    }
}