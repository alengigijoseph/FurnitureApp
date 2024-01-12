package com.example.customar.ui.auth

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.customar.R
import com.example.customar.databinding.ActivityOtpBinding
import com.example.customar.ui.common.MainActivity
import com.example.customar.utils.Constants
import org.json.JSONException
import org.json.JSONObject

class OtpActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var otp: String
    lateinit var binding: ActivityOtpBinding
    lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val otpp = intent.getStringExtra("otp")
        phone = intent.getStringExtra("phone")!!

        binding.textView13.text = "Enter the OTP sent to +91-${phone}"

        Toast.makeText(applicationContext,"OTP is $otpp",Toast.LENGTH_SHORT).show()
        val button = binding.button6
        val etDigit1 = binding.etDigit1
        val etDigit2 = binding.etDigit2
        val etDigit3 = binding.etDigit3
        val etDigit4 = binding.etDigit4
        val etDigit5 =binding.etDigit5

        setOtpTextWatcher(etDigit1, etDigit2)
        setOtpTextWatcher(etDigit2, etDigit3)
        setOtpTextWatcher(etDigit3, etDigit4)
        setOtpTextWatcher(etDigit4, etDigit5)

        showKeyboard(etDigit1)

        button.setOnClickListener{
            Authenticate()
        }
    }

    private fun Authenticate() {

            val queue2 = Volley.newRequestQueue(this)
            val loginUrl = "${Constants.BASE_URL}/auth/verifyotp"
            val loginParams = JSONObject().apply {
                put("phoneNumber", phone)
                put("userOtp", getEnteredOtp()) // Update the OTP value here
            }
        val OtpRequest = JsonObjectRequest(
            Request.Method.POST, loginUrl, loginParams,
            { response ->

                try {
                    val usernamee = response.getString("name")
                    val editor = sharedPreferences.edit()
                    val userId = response.getString("userId")
                    editor.putString("userId", userId)
                    editor.putString("name", usernamee)
                    editor.apply()

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } catch (e: JSONException) {
                    Log.e(ContentValues.TAG, "JSON Parsing Error: $e")
                }
            },
            { error ->

                Log.e(ContentValues.TAG, "Login Error: $error")
            })

        queue2.add(OtpRequest)

    }

    private fun setOtpTextWatcher(currentEditText: EditText, nextEditText: EditText) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    nextEditText.requestFocus()
                    showKeyboard(nextEditText)
                }
            }
        })
    }
    private fun getEnteredOtp(): Int {
        val etDigit1 = binding.etDigit1
        val etDigit2 = binding.etDigit2
        val etDigit3 = binding.etDigit3
        val etDigit4 = binding.etDigit4
        val etDigit5 = binding.etDigit5

        val otpString = etDigit1.text.toString() +
                etDigit2.text.toString() +
                etDigit3.text.toString() +
                etDigit4.text.toString() +
                etDigit5.text.toString()

        return otpString.toIntOrNull() ?: 0 // Convert to integer or fallback to 0
    }
    private fun showKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}