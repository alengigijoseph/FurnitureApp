package com.example.customar.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.customar.databinding.ActivityRegisterBinding
import com.example.customar.utils.Constants.BASE_URL
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {


    private lateinit var name: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var buttonSignup: Button
    lateinit var logg: TextView

    lateinit var fade: Fade
    lateinit var decor: View


    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        decor = window.decorView
        fade = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground,true)
        fade.excludeTarget(android.R.id.navigationBarBackground,true)
        window.enterTransition = fade
        window.exitTransition = fade



        name = binding.regname
        phone = binding.regphone
        password =binding.regpass
        buttonSignup = binding.btnRegister

        logg= binding.logg
        logg.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0,0)
            finish()
        }


        buttonSignup.setOnClickListener {
            submitForm()

        }

        //emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()



    }

    fun doSignup(){

        val queue = Volley.newRequestQueue(this)
        val username = name.text.toString()
        val password = password.text.toString()
        val phone = phone.text.toString()

        val signupUrl = "$BASE_URL/auth/signupphone"
        val signupParams = JSONObject().apply {
            put("phoneNumber", phone)
            put("password", password)
            put("name", username)
        }

        val signupRequest = JsonObjectRequest(
            Request.Method.POST, signupUrl, signupParams,
            { response ->

                val otp = response.getString("message")
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("phone", phone)
                intent.putExtra("otp", otp)
                startActivity(intent)
                finish()
            },
            { error ->
                Log.e(TAG, "Signup Error: $error")
            })

        queue.add(signupRequest)

    }

    /*private fun emailFocusListener()
    {
        email.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regemaill.helperText = validEmail()
            }
        }
    }
    private fun validEmail(): String?
    {
        val emailText = email.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }*/

    private fun passwordFocusListener()
    {
        password.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regpasscontain.helperText = validPassword()
            }
        }
    }
    private fun validPassword(): String?
    {
        val passwordText = password.text.toString()
        if(passwordText.length < 8)
        {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }

    private fun invalidForm()
    {
        var message = ""
        if(binding.regphonecontain.helperText != null)
            message += "\n\nEmail: " + binding.regphonecontain.helperText
        if(binding.regpasscontain.helperText != null)
            message += "\n\nPassword: " + binding.regpasscontain

    }

    private fun submitForm()
    {
        binding.regphonecontain.helperText = validPhone()
       binding.regpasscontain.helperText = validPassword()


        val validPhone = binding.regphonecontain.helperText == null
        val validPassword = binding.regpasscontain.helperText == null

        if (validPhone && validPassword)
            doSignup()
        else
            invalidForm()
    }

    private fun phoneFocusListener()
    {
        binding.regphone.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.regphonecontain.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.regphone.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Must be all Digits"
        }
        if(phoneText.length != 10)
        {
            return "Must be 10 Digits"
        }
        return null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

