package com.example.customar.ui.auth.intro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customar.R
import com.example.customar.databinding.ActivityIntroBinding
import com.example.customar.ui.auth.LoginActivity
import com.example.customar.ui.common.MainActivity

class IntroActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val useridd = sharedPreferences.getString("userId", null)

        if (useridd != null) {
            // User is already logged in, proceed to the main activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.button5.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}