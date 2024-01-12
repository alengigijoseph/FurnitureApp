package com.example.customar.ui.user.pers.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.customar.R
import com.example.customar.databinding.ActivityAddAddressBinding

class AddAddressActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stateSpinner = binding.statesspinner
        val statesArray = resources.getStringArray(R.array.indian_states)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statesArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, AddressesActivity::class.java))
        finish()
    }
}