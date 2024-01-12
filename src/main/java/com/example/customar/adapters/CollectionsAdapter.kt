package com.example.customar.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.R
import com.example.customar.models.Collections

class CollectionsAdapter(var list : MutableList<Collections>): RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>(){

    inner class CollectionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //val itemname: TextView = itemView.findViewById(R.id.collectionname)
        val itemimage: ImageView = itemView.findViewById(R.id.collectionimage)
        val contain: CardView = itemView.findViewById(R.id.collectioncard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.collections_container,parent,false)
        return CollectionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val currentItem = list[position]

        //holder.itemname.text = currentItem.itemName
        holder.itemimage.setImageResource(currentItem.image)
    }
}