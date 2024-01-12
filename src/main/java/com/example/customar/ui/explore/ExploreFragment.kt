package com.example.customar.ui.explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.customar.adapters.ExploreAdapter
import com.example.customar.databinding.FragmentExploreBinding
import com.example.customar.ui.common.NotificationActivity
import com.example.customar.ui.common.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ExploreFragment : Fragment() {

    lateinit var binding: FragmentExploreBinding
    private var selectedViewPagerPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        if (savedInstanceState != null) {
            selectedViewPagerPosition = savedInstanceState.getInt("selectedPosition", 0)
        }
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        val adapter = ExploreAdapter(requireContext(),requireFragmentManager(), requireActivity().lifecycle)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedViewPagerPosition = position
            }
        })
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = adapter.getTabView(position)
        }.attach()

        binding.explorenotification.setOnClickListener{
            startActivity(Intent(requireActivity(), NotificationActivity::class.java))
        }
        binding.exploresearch.setOnClickListener{
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedPosition", selectedViewPagerPosition)
    }
}
