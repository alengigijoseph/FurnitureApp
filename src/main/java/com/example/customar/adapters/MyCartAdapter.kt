package com.example.customar.adapters

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customar.R
import com.example.customar.models.CartItems
import com.example.customar.models.Item
import com.example.customar.ui.cart.CartFragment
import com.example.customar.ui.common.ItemInfoActivity
import com.example.customar.ui.common.MainActivity
import com.example.customar.utils.Constants.BASE_URL

class MyCartAdapter(var context: Context,var list: MutableList<CartItems>, private val onItemClick: (CartItems) -> Unit, private val cartAdapterClickListener: CartAdapterClickListener) :
    RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder>() {

    interface CartAdapterClickListener {
        fun onAddButtonClicked(position: Int)
        fun onMinusButtonClicked(position: Int)
        fun onWishlistClicked(position: Int)
    }

    fun getItemAtPosition(position: Int): CartItems? {
        return list[position]
    }


    inner class MyCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.cartimage)
        val itemname: TextView = itemView.findViewById(R.id.cartitemname)
        val categorycontain: ConstraintLayout = itemView.findViewById(R.id.cartcontain)
        val orgprice: TextView = itemView.findViewById(R.id.cartogprice)
        val offerprice: TextView = itemView.findViewById(R.id.cartofferprice)
        val quantity: TextView = itemView.findViewById(R.id.cartcount)
        val add: ImageButton = itemView.findViewById(R.id.cartadd)
        val minus: ImageButton = itemView.findViewById(R.id.cartdelete)
        val wish: ImageButton = itemView.findViewById(R.id.cartlike)

        init {
            categorycontain.setOnClickListener {
                val position = adapterPosition
                val selectedItem = getItemAtPosition(position)
                selectedItem?.let { onItemClick(it) }
            }
            add.setOnClickListener {
                val position = adapterPosition
                cartAdapterClickListener.onAddButtonClicked(position)
            }
            minus.setOnClickListener{
                val position = adapterPosition
                cartAdapterClickListener.onMinusButtonClicked(position)
            }
            wish.setOnClickListener{
                val position = adapterPosition
                cartAdapterClickListener.onWishlistClicked(position)
        }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_container, parent, false)
        return MyCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val currentItem = list[position]

        if(currentItem.quantity > 1){
            holder.minus.setImageResource(R.drawable.ic_minus)
        }

        if (currentItem.item.isWishlisted) {
            holder.wish.setImageResource(R.drawable.ic_liked)
        //Toast.makeText(context,"Added to Wishlist",Toast.L)// Change this to your "liked" icon
        } else {
            holder.wish.setImageResource(R.drawable.ic_heart) // Change this to your "like" icon
        }

        holder.itemname.text = currentItem.item.itemName
        holder.quantity.text = currentItem.quantity.toString()
        val url = BASE_URL + "/"+currentItem.item.image[0]
        Glide.with(holder.itemView)
            .load(url)
            .into(holder.itemImage)

        holder.offerprice.text = "₹ "+ currentItem.item.offerPrice

        val spannablePrice = SpannableString("₹ " + currentItem.item.price)
        spannablePrice.setSpan(
            StrikethroughSpan(),
            0,
            spannablePrice.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.orgprice.text = spannablePrice


    }

    override fun getItemCount(): Int {
        return list.size
    }

}