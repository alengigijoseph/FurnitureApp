package com.example.customar.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.customar.R
import com.example.customar.databinding.FragmentBlogBinding

class BlogFragment : Fragment() {

    private lateinit var webView: WebView
    lateinit var binding: FragmentBlogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlogBinding.inflate(layoutInflater)

        webView = binding.webView
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()
        val websiteUrl = "https://www.decorvue.in/editorial"
        webView.loadUrl(websiteUrl)

        return binding.root

    }

}