package com.example.customar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customar.R
import com.example.customar.models.Item
import com.example.customar.models.ItemName

class SearchAdapter(private var results: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    fun getItemAtPosition(position: Int): String? {
        return results[position]
    }

    fun setData(newResults: List<String>) {
        results = newResults
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultTextView: TextView = itemView.findViewById(R.id.searchitemname)
        val searchcontain: ConstraintLayout = itemView.findViewById(R.id.searchitemcontain)

        init {
            searchcontain.setOnClickListener{
                val position = adapterPosition
                val selectedItem = getItemAtPosition(position)
                selectedItem?.let { onItemClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.resultTextView.text = results[position]
    }

    override fun getItemCount(): Int {
        return results.size
    }
}