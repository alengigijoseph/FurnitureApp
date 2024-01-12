package com.example.customar.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.R
import com.example.customar.models.Essentials

class EssentialsAdapter(private val dataList: MutableList<Essentials>) : RecyclerView.Adapter<EssentialsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.essentials_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.itemView.findViewById<ImageView>(R.id.essentialsimage).setImageResource(item.image)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
           /* val intent = Intent(context, DestinationActivity::class.java)
            // Pass any necessary data to the DestinationActivity if needed
            // For example, you can use intent.putExtra() to send data to the destination activity.
            context.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
