package com.example.customar.ui.user.pers.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.customar.R
import com.example.customar.databinding.ActivityAddressesBinding

class AddressesActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddressesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button7.setOnClickListener{
            startActivity(Intent(this, AddAddressActivity::class.java))
            finish()
        }


    }
}