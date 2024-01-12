package com.example.customar.ui.rentals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customar.databinding.FragmentRentalsBinding
import com.example.customar.ui.common.NotificationActivity
import com.example.customar.ui.common.SearchActivity

class RentalsFragment : Fragment() {

    lateinit var binding: FragmentRentalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentalsBinding.inflate(inflater,container,false)

        binding.rentsearchBar.setOnClickListener{
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        binding.rentnotification.setOnClickListener{
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }


        return binding.root
    }

}