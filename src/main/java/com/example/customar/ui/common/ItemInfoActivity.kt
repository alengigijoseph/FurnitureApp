package com.example.customar.ui.common

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.example.customar.R
import com.example.customar.adapters.ItemImagesAdapter
import com.example.customar.databinding.ActivityItemInfoBinding
import com.example.customar.models.CartResponse
import com.example.customar.models.Item
import com.example.customar.models.Wishlist
import com.example.customar.models.WishlistRequest
import com.example.customar.network.ApiService
import com.example.customar.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityItemInfoBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var imagesvp: ViewPager2
    lateinit var apiService: ApiService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitBuilder.apiService
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null).toString()
        val cartcount = sharedPreferences.getInt("cart",0).toString()

        /*binding.cartiteminfo.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("fragment","cart")
            startActivity(intent)
        }*/

        binding.iteminfoback.setOnClickListener{
            onBackPressed()
        }

        binding.cartcountiteminfo.text = cartcount

        val selectedItem = intent.getParcelableExtra<Item>("selectedItem")
        val itemId= selectedItem?.itemId.toString()

        binding.iteminfowish.setOnClickListener{
           addItemToWishlist(userId,itemId)
        }

        binding.addtocart.setOnClickListener{
            addItemToCart(userId, itemId)
        }


        if (selectedItem != null) {
            val spannablePrice = SpannableString("₹ " + selectedItem.price)
            spannablePrice.setSpan(
                StrikethroughSpan(),
                0,
                spannablePrice.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            binding.iteminfoname.text = selectedItem.itemName
            binding.iteminfoacprice.text = spannablePrice
            binding.iteminfoprice.text = selectedItem.offerPrice
            val save = (((selectedItem.price.toInt() - selectedItem.offerPrice.toInt()).toDouble() / selectedItem.price.toDouble()) * 100)
            val trimmedSave = if (save % 1 == 0.0) save.toInt().toString() else save.toString()
            binding.iteminfosaveperc.text = "Save $trimmedSave%"

            if (selectedItem.isWishlisted){
                binding.iteminfowish.setImageResource(R.drawable.ic_liked)
            }
            else{
                binding.iteminfowish.setImageResource(R.drawable.ic_heart)
            }

            imagesvp = binding.iteminfovp
            val imagesAdapter = ItemImagesAdapter(selectedItem.image)


            imagesvp.adapter = imagesAdapter

        } else {
            Toast.makeText(this, "Invalid item data", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun addItemToWishlist(userId: String, itemId: String) {

        val request = WishlistRequest(userId, itemId)

        apiService.addToWishlist(request).enqueue(object : Callback<Wishlist> {
            override fun onResponse(call: Call<Wishlist>, response: Response<Wishlist>) {
                if (response.isSuccessful) {

                    val bodt = response.body()
                    if (bodt!!.added){
                        binding.iteminfowish.setImageResource(R.drawable.ic_liked)
                        Toast.makeText(applicationContext, "Item Added to Wishlist",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        binding.iteminfowish.setImageResource(R.drawable.ic_heart)
                        Toast.makeText(applicationContext, "Item removed from Wishlist",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@ItemInfoActivity, "Failed to add to wishlist", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Wishlist>, t: Throwable) {
                Toast.makeText(this@ItemInfoActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addItemToCart(userId: String, itemId: String) {

        val apiService = RetrofitBuilder.apiService

        val request = WishlistRequest(userId, itemId)

        apiService.addToCart(request).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    val quantity = response.body()
                    val size = quantity?.quantity
                    Toast.makeText(this@ItemInfoActivity, "Item added to cart", Toast.LENGTH_SHORT).show()

                    val editor = sharedPreferences.edit()
                    editor.putInt("cart", size!!)
                    editor.apply()

                    sharedPreferences.edit().putBoolean("itemAddedToCart", true).apply()
                } else {
                    Toast.makeText(this@ItemInfoActivity, "Failed to add to cart", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(this@ItemInfoActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}