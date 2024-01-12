package com.example.customar.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.util.*
import javax.crypto.Mac

object TokenManager {

    private const val PREF_NAME = "TOKEN_PREF"
    const val USER_TOKEN = "usertoken"

    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signToken(secret: String, data: ByteArray): String {
        val signature = Mac.getInstance("HmacSHA256")
        val key = javax.crypto.spec.SecretKeySpec(secret.toByteArray(), "HmacSHA256")
        signature.init(key)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signature.doFinal(data))
    }

    fun saveAuthToken(context: Context, token: String) {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


}
