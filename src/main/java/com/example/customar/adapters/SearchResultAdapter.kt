package com.example.customar.adapters

import android.media.Rating
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customar.R
import com.example.customar.models.Item
import com.example.customar.utils.Constants.BASE_URL

class SearchResultAdapter(private val onItemClick: (Item) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder>() {

    private val items = mutableListOf<Item>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_container, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = items[position]
        holder.firstitemname.text = item.itemName
        holder.firstorgprice.text = item.price
        holder.firstofferprice.text = item.offerPrice
        val url = item.image[0]
        Glide.with(holder.itemView)
            .load(url)
            .placeholder(R.drawable.ic_spinner)
            .into(holder.firstitemImage)

    }

    fun getItemAtPosition(position: Int): Item? {

       return  items[position]

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val firstitem: ConstraintLayout = itemView.findViewById(R.id.firstItemcontain)
        val firstitemImage: ImageView = itemView.findViewById(R.id.firstitemimage)
        val firstitemname: TextView = itemView.findViewById(R.id.firstitemname)
        val firstorgprice: TextView = itemView.findViewById(R.id.firstitemorgprice)
        val firstofferprice: TextView = itemView.findViewById(R.id.firstitemofferprice)
        val firstofferperc: TextView = itemView.findViewById(R.id.firstitemoffperc)
        val firstRating: View? = itemView.findViewById(R.id.firstitemrating)
        val firstWish: View? = itemView.findViewById(R.id.firstitemwish)

        init {
            firstitem.setOnClickListener{
                val position = adapterPosition
                val selectedItem = getItemAtPosition(position)
                selectedItem?.let { onItemClick(it) }

            }
        }

    }

}