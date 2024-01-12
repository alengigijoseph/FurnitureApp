package com.example.customar.ui.shop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.customar.R
import com.example.customar.adapters.ImageAdapter
import com.example.customar.adapters.NewArrivalAdapter
import com.example.customar.databinding.FragmentShopBinding
import com.example.customar.models.Item
import com.example.customar.network.RetrofitBuilder
import com.example.customar.ui.common.ItemInfoActivity
import com.example.customar.ui.common.NotificationActivity
import com.example.customar.ui.common.SearchActivity
import com.example.customar.ui.common.SearchResultActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.badge.BadgeDrawable
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ShopFragment : Fragment() {


    lateinit var binding: FragmentShopBinding
    lateinit var notification: ImageButton
    lateinit var cartBadge: BadgeDrawable
    lateinit var newarrivalAdapter: NewArrivalAdapter
    lateinit var newarrivalrv: RecyclerView
    private lateinit var  viewPager: ViewPager2
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    lateinit var handler: Handler
    lateinit var layoutParams: ViewGroup.LayoutParams
    private val client = OkHttpClient()
    lateinit var newarrshimmer: ShimmerFrameLayout

    private var isResumedForTheFirstTime = true
    private var cachedItems: List<Item>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShopBinding.inflate(inflater, container, false)

        newarrshimmer = binding.shimmernewarrival
        notification = binding.homenotification


        newarrivalrv = binding.furniturenewrv
        init()
        setUpTransformer()

        if (cachedItems != null) {
            updateRecyclerView(cachedItems!!)
            newarrshimmer.visibility = View.INVISIBLE
        }

        binding.sofafur.setOnClickListener{
            fetchCategoryItem("Sofa")
        }
        binding.chairfur.setOnClickListener{
            fetchCategoryItem("Chair")
        }
        binding.Tablefur.setOnClickListener{
            fetchCategoryItem("Tables")
        }
        binding.bedfur.setOnClickListener{
            fetchCategoryItem("Bed")
        }
        binding.closetfur.setOnClickListener{
            fetchCategoryItem("Closet")
        }
        binding.Tvstandfur.setOnClickListener{
            fetchCategoryItem("TV Stand")
        }
        binding.Kids.setOnClickListener{
            fetchCategoryItem("Kids")
        }
        binding.officefur.setOnClickListener{
            fetchCategoryItem("Office")
        }

        binding.homesearchBar.setOnClickListener{
            startActivity(Intent(requireContext(), SearchActivity::class.java))

        }
        binding.homenotification.setOnClickListener{
            startActivity(Intent(requireContext(),NotificationActivity::class.java))
        }

        return binding.root
    }



    private fun fetchCategoryItem(query: String) {
        val url = "${com.example.customar.utils.Constants.BASE_URL}/items/search?query=$query"
        Log.d("url",url)

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {

            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val responseData = response.body?.string()
                activity?.runOnUiThread {
                    try {
                        if (response.isSuccessful && responseData != null) {
                            val items = Gson().fromJson(responseData, Array<Item>::class.java)
                            val intent = Intent(requireActivity(), SearchResultActivity::class.java)
                            intent.putExtra("items", ArrayList(items.toList()))
                            intent.putExtra("item",query)
                            startActivity(intent)
                        } else {

                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            requireActivity(),
                            "Error fetching data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }


     fun getNewArrivals() {

        val apiService = RetrofitBuilder.apiService
        val call = apiService.getRecentItems()
        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(
                call: Call<List<Item>>,
                response: Response<List<Item>>
            ) {
                if (response.isSuccessful) {

                    val items = response.body()
                    if (!items.isNullOrEmpty()) {

                        cachedItems = items
                        updateRecyclerView(items)
                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {

            }
        })
    }

    private fun updateRecyclerView(items: List<Item>) {
        newarrivalAdapter = NewArrivalAdapter(items.toMutableList()) { selectedItem ->
            val intent = Intent(requireContext(), ItemInfoActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }

        newarrivalrv.adapter = newarrivalAdapter
        newarrshimmer.visibility = View.INVISIBLE

    }

    private val runnable = Runnable {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun init(){
        viewPager = binding.offerzonevp

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 5000)
            }
        })

        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.offer1)
        imageList.add(R.drawable.offer2)
        imageList.add(R.drawable.offer3)
        imageList.add(R.drawable.offer4)

        adapter = ImageAdapter(imageList, viewPager)
        adapter.setOnItemClickListener(object: ImageAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                if(position == 1){
                    Toast.makeText(activity, "Item clicked: $position", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewPager.adapter = adapter
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val indicator = binding.shopci
        indicator.setViewPager(viewPager)

    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(10))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager.setPageTransformer(transformer)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        if (isResumedForTheFirstTime) {
            getNewArrivals()
            isResumedForTheFirstTime = false
        }
        handler.postDelayed(runnable , 5000)
    }


}