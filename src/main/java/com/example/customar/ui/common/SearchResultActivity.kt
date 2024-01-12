package com.example.customar.ui.common


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customar.R
import com.example.customar.adapters.SearchResultAdapter
import com.example.customar.databinding.ActivitySearchResultBinding
import com.example.customar.models.Item


class SearchResultActivity : AppCompatActivity() {

    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemsAdapter: SearchResultAdapter

    lateinit var binding: ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemsRecyclerView = findViewById(R.id.searcheditemsrv)

        itemsAdapter = SearchResultAdapter{ selectedItem ->
            // Handle the clicked item as needed
            val intent = Intent(this, ItemInfoActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }

        itemsRecyclerView.layoutManager = GridLayoutManager(this,2)
        itemsRecyclerView.adapter = itemsAdapter

        val items = intent.getParcelableArrayListExtra<Item>("items")
        val itemname = intent.extras?.getString("item")
        binding.sritemname.text = itemname
        if (!items.isNullOrEmpty()) {
            itemsAdapter.setItems(items)

        } else {

            Toast.makeText(this,"error fetching data", Toast.LENGTH_SHORT).show()
        }


    }

   /* override fun onItemClick(position: Int) {
        val selectedItem = itemsAdapter.getItemAtPosition(position)

        if (selectedItem != null) {
            val intent = Intent(this, ItemInfoActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid item position", Toast.LENGTH_SHORT).show()
        }
    }*/


}