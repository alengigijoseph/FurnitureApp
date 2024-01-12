package com.example.customar.ui.user

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customar.databinding.FragmentUserBinding
import com.example.customar.ui.auth.LoginActivity
import com.example.customar.ui.user.helpnsupport.CustomerSuppActivity
import com.example.customar.ui.user.pers.address.AddressesActivity
import com.example.customar.ui.user.pers.AppSettingsActivity
import com.example.customar.ui.user.pers.PaymentsActivity
import com.example.customar.ui.user.top.AccountActivity
import com.example.customar.ui.user.top.CollectionsActivity
import com.example.customar.ui.user.top.OrdersActivity
import com.example.customar.ui.user.top.WishlistActivity


class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserBinding.inflate(inflater, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val usernam = sharedPreferences.getString("name", null)?.split(" ")?.get(0)
        binding.hellouser.text = "Hello, $usernam"

        binding.yourorders.setOnClickListener {
            startActivity(Intent(requireContext(), OrdersActivity::class.java))
        }
        binding.youraccount.setOnClickListener {
            startActivity(Intent(requireContext(), AccountActivity::class.java))
        }
        binding.yourcollections.setOnClickListener {
            startActivity(Intent(requireContext(), CollectionsActivity::class.java))
        }
        binding.yourwishlist.setOnClickListener {
            startActivity(Intent(requireContext(), WishlistActivity::class.java))
        }
        binding.appsettings.setOnClickListener {
            startActivity(Intent(requireContext(), AppSettingsActivity::class.java))
        }

        binding.youraddress.setOnClickListener {
            startActivity(Intent(requireContext(), AddressesActivity::class.java))
        }
        binding.yourpayment.setOnClickListener {
            startActivity(Intent(requireContext(), PaymentsActivity::class.java))
        }
        binding.contactus.setOnClickListener {
            openGmailCompose()
        }
        binding.cussupport.setOnClickListener {
            startActivity(Intent(requireContext(), CustomerSuppActivity::class.java))
        }
        binding.rateapp.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder
                .setMessage("Would you mind taking a moment to rate it on the App Store? it won't take more than a minute. Thanks for your support!")
                .setPositiveButton("RATE NOW") { _, _ ->
                }
                .setNegativeButton("REMIND ME LATER") { _, _ ->
                }
            builder.create()
            builder.show()
        }

        binding.logout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
        }

        return binding.root
    }
    fun openGmailCompose() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("recipient@example.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            putExtra(Intent.EXTRA_TEXT, "Body of the email")
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }


}