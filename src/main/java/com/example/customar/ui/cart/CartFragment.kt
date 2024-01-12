package com.example.customar.ui.cart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.adapters.MyCartAdapter
import com.example.customar.databinding.FragmentCartBinding
import com.example.customar.models.CartItems
import com.example.customar.models.CartResponse
import com.example.customar.models.Item
import com.example.customar.models.Wishlist
import com.example.customar.models.WishlistRequest
import com.example.customar.network.RetrofitBuilder
import com.example.customar.ui.common.ItemInfoActivity
import com.example.customar.ui.common.MainActivity
import com.example.customar.ui.common.NotificationActivity
import com.example.customar.ui.common.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment() , MyCartAdapter.CartAdapterClickListener {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var cartAdapter: MyCartAdapter
    lateinit var cartRv: RecyclerView
    lateinit var binding: FragmentCartBinding
     var cost: Int = 0
    var size: Int = 0
    lateinit var list: ArrayList<Item>

    private var isResumedForTheFirstTime = true
    private var cachedItems: List<CartItems>? = null
    private var cachedCost: Int = 0
    private var cachedQuantity: Int = 0
    private lateinit var parentActivity: MainActivity
    private var fragmentContext: Context? = null


    lateinit var overlay: View
    lateinit var loadingSpinner: ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
        parentActivity = context as MainActivity
    }
    override fun onDetach() {
        super.onDetach()
        fragmentContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(inflater, container, false)
        cartRv = binding.cartRv
        loadingSpinner = binding.loadingSpinner
        overlay = binding.overlay
        cost = 0
        size = 0
        list = ArrayList<Item>()
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (isAdded && cachedItems != null) {
            binding.constraintLayout10.visibility = View.GONE
            binding.carttotal.text = "₹ $cachedCost"
            binding.proceedtobuy.text = "Proceed to buy $cachedQuantity items"
            updateRecyclerView(cachedItems!!)
        } else {
            overlay.visibility = View.GONE
            loadingSpinner.visibility = View.GONE
        }
        binding.cartnotification.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }
        binding.cartsearchBar.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }

        return binding.root
    }

    private fun updateRecyclerView(items: List<CartItems>) {
        cartAdapter = MyCartAdapter(requireContext(),items.toMutableList(),{
                selectedItem ->
            val intent = Intent(requireContext(), ItemInfoActivity::class.java)
            intent.putExtra("selectedItem", selectedItem.item)
            startActivity(intent)
        },this)

        cartRv.adapter = cartAdapter
        overlay.visibility = View.GONE
        loadingSpinner.visibility = View.GONE

    }

    private fun getCartItems() {
        val context = fragmentContext ?: return
        overlay.visibility = View.VISIBLE
        loadingSpinner.visibility = View.VISIBLE
        val userId = sharedPreferences.getString("userId", null).toString()

        val apiService = RetrofitBuilder.apiService
        val call = apiService.getCartItems(userId)
        call.enqueue(object : Callback<List<CartItems>> {
            override fun onResponse(
                call: Call<List<CartItems>>,
                response: Response<List<CartItems>>
            ) {
                if (response.isSuccessful) {

                    val items = response.body()
                    if (!items.isNullOrEmpty()) {
                        cost = 0
                        size = 0
                        for(i in items){
                            cost+= i.item.offerPrice.toInt()* i.quantity
                            size+= i.quantity
                            list.add(i.item)
                        }
                        cachedCost = cost
                        cachedQuantity = size
                        parentActivity.cartBadge.number = size
                        parentActivity.cartBadge.isVisible = true
                        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("cart", size)
                        editor.apply()
                        binding.carttotal.text = "₹ ${cost}"
                        binding.constraintLayout10.visibility = View.GONE
                        binding.proceedtobuy.text = "Proceed to buy ${size} items"
                        cachedItems = items
                        updateRecyclerView(items)


                    } else {
                        cachedItems = null
                        cachedCost = 0
                        cachedQuantity = 0
                        parentActivity.cartBadge.isVisible = false
                        val editor = sharedPreferences.edit()
                        editor.remove("cart")
                        editor.apply()
                        binding.carttotal.text = "₹ 0"
                        binding.proceedtobuy.text = "Proceed to buy 0 Items"
                        binding.constraintLayout10.visibility = View.VISIBLE
                        overlay.visibility = View.GONE
                        loadingSpinner.visibility = View.GONE
                    }
                } else {
                    overlay.visibility = View.GONE
                    loadingSpinner.visibility = View.GONE
                    binding.constraintLayout10.visibility = View.VISIBLE
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CartItems>>, t: Throwable) {
                overlay.visibility = View.GONE
                loadingSpinner.visibility = View.GONE
                binding.constraintLayout10.visibility = View.VISIBLE
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()

            }
        })
    }
    override fun onResume() {
        super.onResume()
        if (isResumedForTheFirstTime) {
            getCartItems()
            isResumedForTheFirstTime = false
        }
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val itemAddedToCart = sharedPreferences.getBoolean("itemAddedToCart", false)

        if (itemAddedToCart) {
            getCartItems()
            sharedPreferences.edit().putBoolean("itemAddedToCart", false).apply()
        }
    }

    override fun onAddButtonClicked(position: Int) {
        val selectedItem = cachedItems?.get(position)
        if (selectedItem != null) {
            val userId = sharedPreferences.getString("userId", null).toString()
            addItemToCart(userId, selectedItem.item.itemId)
        }
    }

    override fun onMinusButtonClicked(position: Int) {
        val selectedItem = cachedItems?.get(position)
        if (selectedItem != null) {
            val userId = sharedPreferences.getString("userId", null).toString()
            deleteCartItem(userId, selectedItem.item.itemId)
        }
    }

    override fun onWishlistClicked(position: Int) {
        val selectedItem = cachedItems?.get(position)
        if (selectedItem != null) {
            val userId = sharedPreferences.getString("userId", null).toString()
            addItemToWishlist(userId, selectedItem.item.itemId)
        }
    }

    private fun addItemToCart(userId: String, itemId: String) {

        val apiService = RetrofitBuilder.apiService

        val request = WishlistRequest(userId, itemId)

        apiService.addToCart(request).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    getCartItems()
                } else {
                    Toast.makeText(requireContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun addItemToWishlist(userId: String, itemId: String) {

        val apiService = RetrofitBuilder.apiService

        val request = WishlistRequest(userId, itemId)

        apiService.addToWishlist(request).enqueue(object : Callback<Wishlist> {
            override fun onResponse(call: Call<Wishlist>, response: Response<Wishlist>) {
                if (response.isSuccessful) {
                   getCartItems()
                    val body = response.body()
                    if (body!!.added){
                        Toast.makeText(requireContext(), "Item Added to Wishlist",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "Item removed from Wishlist",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(requireContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Wishlist>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteCartItem(userId: String, itemId: String) {

        val apiService = RetrofitBuilder.apiService

        val request = WishlistRequest(userId, itemId)

        apiService.deleteCart(request).enqueue(object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if (response.isSuccessful) {
                    getCartItems()
                } else {
                    Toast.makeText(requireContext(), "Failed to delete cart item", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}