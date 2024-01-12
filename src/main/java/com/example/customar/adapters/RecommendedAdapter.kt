package com.example.customar.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.models.Item

class RecommendedAdapter(var list: MutableList<Item>) {

    inner class RecommendedViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

    }
}