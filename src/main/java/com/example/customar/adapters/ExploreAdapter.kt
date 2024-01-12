package com.example.customar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.customar.R
import com.example.customar.ui.explore.BlogFragment
import com.example.customar.ui.explore.TrendingFragment
import com.example.customar.ui.explore.RoomsFragment
import com.example.customar.ui.explore.EssentialsFragment

class ExploreAdapter(private val context: Context,fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val tabIcons = listOf(R.drawable.trending, R.drawable.essentials, R.drawable.byrooom, R.drawable.blog)
    private val namee = listOf("Whats New", "Essentials", "Style by Room", "Editorial")

    override fun getItemCount(): Int {
        return tabIcons.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrendingFragment()
            1 -> EssentialsFragment()
            2 -> RoomsFragment()
            3 -> BlogFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }

    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null)
        val tabIcon = view.findViewById<ImageView>(R.id.tabIcon)
        val name = view.findViewById<TextView>(R.id.exitemname)
        tabIcon.setImageResource(tabIcons[position])
        name.text = namee[position]
        return view
    }

    // Rest of the ExploreAdapter implementation
}
/*

class ExploreAdapter(private val context: Context,fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val tabIcons = listOf(R.drawable.trending, R.drawable.essentials, R.drawable.byrooom, R.drawable.blog)
    private val namee = listOf("Trending", "Essentials","Style by Room","Blog")

    override fun getItemCount(): Int {
        return tabIcons.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TrendingFragment()
            1 -> EssentialsFragment()
            2 -> RoomsFragment()
            3 -> BlogFragment()
            else -> throw IllegalStateException("Invalid position: $position")
        }

    }

    fun getTabView(position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null)
        val tabIcon = view.findViewById<ImageView>(R.id.tabIcon)
        val name = view.findViewById<TextView>(R.id.exitemname)
        tabIcon.setImageResource(tabIcons[position])
        name.text = namee[position]
        return view
    }
}
*/
