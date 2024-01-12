package com.example.customar.adapters

import android.text.SpannableString
import android.text.style.StrikethroughSpan
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

class NewArrivalAdapter(var list: MutableList<Item>, private val onItemClick: (Item) -> Unit) :
    RecyclerView.Adapter<NewArrivalAdapter.NewArrivalViewHolder>() {

    fun getItemAtPosition(position: Int): Item? {
        return list[position]
    }


    inner class NewArrivalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.newimage22)
        val itemname: TextView = itemView.findViewById(R.id.newarritemname200)
        val categorycontain: ConstraintLayout = itemView.findViewById(R.id.newarrivalcontain)
        val orgprice: TextView = itemView.findViewById(R.id.seconditemorgprice)
        val offerprice: TextView = itemView.findViewById(R.id.offerpricenewarr100)

        init {
            categorycontain.setOnClickListener {
                val position = adapterPosition
                val selectedItem = getItemAtPosition(position)
                selectedItem?.let { onItemClick(it) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewArrivalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_arrival_container, parent, false)
        return NewArrivalViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewArrivalViewHolder, position: Int) {
        val currentItem = list[position]

        holder.itemname.text = currentItem.itemName
        val url = BASE_URL + "/"+currentItem.image[0]
        Glide.with(holder.itemView)
            .load(url)
            .into(holder.itemImage)

        holder.offerprice.text = "₹ "+ currentItem.offerPrice

        val spannablePrice = SpannableString("₹ " + currentItem.price)
        spannablePrice.setSpan(
            StrikethroughSpan(),
            0,
            spannablePrice.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        holder.orgprice.text = spannablePrice

        /*if (!currentItem.offerPrice.isNullOrEmpty()) {
            val spannableOfferPrice = SpannableString("₹ " + currentItem.price)
            spannableOfferPrice.setSpan(
                StrikethroughSpan(),
                0,
                spannableOfferPrice.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            holder.orgprice.text = spannableOfferPrice
        } else {
            holder.orgprice.text =  "₹ " + currentItem.price
        }*/

    }

    override fun getItemCount(): Int {
        return list.size
    }

}