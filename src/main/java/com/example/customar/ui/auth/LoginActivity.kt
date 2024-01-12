package com.example.customar.ui.auth

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.customar.R
import com.example.customar.ui.auth.intro.IntroActivity
import com.example.customar.ui.common.MainActivity
import com.example.customar.utils.Constants.BASE_URL
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var login: Button
    lateinit var phone: EditText
    lateinit var password:EditText
    lateinit var reg: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        phone = findViewById(R.id.logphone)
        password = findViewById(R.id.logpass)
        reg = findViewById(R.id.regg)

        reg.setOnClickListener{

            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }


        login = findViewById(R.id.btnLogin)
        login.setOnClickListener {

            val phone = phone.text.toString()
            val pass = password.text.toString()

            val queue2 = Volley.newRequestQueue(this)
            val loginUrl = "$BASE_URL/auth/loginphone"
            val loginParams = JSONObject().apply {
                put("phoneNumber", phone)
                put("password", pass)
            }

            val loginRequest = JsonObjectRequest(
                Request.Method.POST, loginUrl, loginParams,
                { response ->

                    val otp = response.getString("message")
                    val intent = Intent(this, OtpActivity::class.java)
                    intent.putExtra("phone", phone)
                    intent.putExtra("otp", otp)
                    startActivity(intent)
                    finish()
                },
                { error ->
                    Log.e(TAG, "Login Error: $error")
                })

            queue2.add(loginRequest)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }


}