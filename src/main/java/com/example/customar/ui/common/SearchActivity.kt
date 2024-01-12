package com.example.customar.ui.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.R
import com.example.customar.adapters.SearchAdapter
import com.example.customar.models.Item
import com.example.customar.network.ApiService
import com.example.customar.network.RetrofitBuilder
import com.example.customar.utils.Constants
import com.example.customar.utils.Constants.BASE_URL
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Timer
import java.util.TimerTask


class SearchActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var autocompleteAdapter: SearchAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var autocompleteResults: List<String>
    private val client = OkHttpClient()

    private val handler = Handler()
    private val delay: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        autocompleteResults = emptyList()

        apiService = RetrofitBuilder.apiService

        recyclerView = findViewById(R.id.searchitemrv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        autocompleteAdapter = SearchAdapter(emptyList()) { selectedItem ->

            fetchSelectedItem(selectedItem)
        }
        recyclerView.adapter = autocompleteAdapter

        val searchView: SearchView = findViewById(R.id.searchitembar)
        searchView.isIconified = false
        searchView.requestFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (!newText.isNullOrEmpty()) {
                    handler.postDelayed({
                        fetchAutocompleteResults(newText)
                    }, delay)
                } else {
                    autocompleteResults = emptyList()
                    autocompleteAdapter.notifyDataSetChanged()
                }
                return true
            }
        })
    }

    private fun fetchAutocompleteResults(query: String) {
        apiService.getAutocompleteResults(query).enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    autocompleteResults = response.body() ?: emptyList()

                    autocompleteAdapter.setData(autocompleteResults)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun fetchSelectedItem(query: String) {
        val url = "${BASE_URL}/items/search?query=$query"
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
                runOnUiThread {
                    try {
                        if (response.isSuccessful && responseData != null) {
                            val items = Gson().fromJson(responseData, Array<Item>::class.java)
                            val intent = Intent(applicationContext, SearchResultActivity::class.java)
                            intent.putExtra("items", ArrayList(items.toList()))
                            intent.putExtra("item",query)
                            startActivity(intent)
                        } else {

                        }
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, "Error fetching data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
