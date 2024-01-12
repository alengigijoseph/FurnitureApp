package com.example.customar.ui.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.customar.R
import com.example.customar.ui.rentals.RentalsFragment
import com.example.customar.ui.cart.CartFragment
import com.example.customar.ui.explore.ExploreFragment
import com.example.customar.ui.shop.ShopFragment
import com.example.customar.ui.user.UserFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    private val fragmentMap: MutableMap<Int, Fragment> = mutableMapOf()
    private lateinit var currentFragment: Fragment
    lateinit var cartBadge: BadgeDrawable
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)
        cartBadge = bottomNav.getOrCreateBadge(R.id.cart)
        cartBadge.backgroundColor = ContextCompat.getColor(this, R.color.orange)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedCartBadgeNumber = sharedPreferences.getInt("cart", 0)
        if(savedCartBadgeNumber ==0){
            cartBadge.isVisible = false
        }else{
            cartBadge.number = savedCartBadgeNumber
            cartBadge.isVisible = true
        }


        fragmentMap[R.id.shop] = ShopFragment()
        fragmentMap[R.id.rent] = RentalsFragment()
        fragmentMap[R.id.explore] = ExploreFragment()
        fragmentMap[R.id.user] = UserFragment()
        fragmentMap[R.id.cart] = CartFragment()

        bottomNav.selectedItemId = R.id.explore
        bottomNav.setOnItemSelectedListener { menuItem ->
            val selectedFragment = fragmentMap[menuItem.itemId] ?: return@setOnItemSelectedListener false

            if (selectedFragment != currentFragment) {
                loadFragment(selectedFragment)
            }
            currentFragment = selectedFragment
            true
        }
        if (savedInstanceState == null) {
            // Initial load
            currentFragment = fragmentMap[R.id.explore]!!
            loadFragment(currentFragment)

            if (currentFragment is ShopFragment) {
                (currentFragment as ShopFragment).getNewArrivals()
            }
        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (!fragment.isAdded) {
                replace(R.id.container, fragment)
            }
            commit()
        }
    }

    override fun onResume() {
        super.onResume()
        val savedCartBadgeNumber = sharedPreferences.getInt("cart", 0)
        if(savedCartBadgeNumber ==0){
            cartBadge.isVisible = false
        }else{
            cartBadge.number = savedCartBadgeNumber
            cartBadge.isVisible = true
        }
    }
}