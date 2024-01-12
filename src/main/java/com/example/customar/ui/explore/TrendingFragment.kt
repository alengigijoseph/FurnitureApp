package com.example.customar.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.customar.R
import com.example.customar.adapters.CollectionsAdapter
import com.example.customar.adapters.EssentialsAdapter
import com.example.customar.databinding.FragmentTrendingBinding
import com.example.customar.models.Collections
import com.example.customar.models.Essentials

class TrendingFragment : Fragment() {

    lateinit var name: Array<String>
    lateinit var image: Array<Int>
    lateinit var rv: RecyclerView
    lateinit var adapter2: CollectionsAdapter
    lateinit var list: ArrayList<Collections>

    lateinit var wishess: Array<Boolean>
    private lateinit var  viewPager: ViewPager2
    private lateinit var imageList:Array<Int>
    private lateinit var adapter: EssentialsAdapter
    lateinit var al2: ArrayList<Essentials>
    lateinit var binding: FragmentTrendingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(inflater,container, false)
        init()

        name = arrayOf("item1", "item1", "item1","item1")
        image = arrayOf(R.drawable.future1,R.drawable.collection1,R.drawable.future2,R.drawable.future3,R.drawable.collection1,R.drawable.future4,R.drawable.future1,R.drawable.collection1,)
        rv = binding.timeRv
        rv.setHasFixedSize(true)
        list = arrayListOf<Collections>()
        getUserdata()

        return binding.root
    }

    fun getUserdata(){
        for(i in image.indices){
            val item= Collections(image[i])
            list.add(item)
        }
        adapter2 = CollectionsAdapter(list)
        rv.adapter = adapter2
    }


    private fun init() {
        viewPager = binding.essentialsvp

        imageList = arrayOf(
            R.drawable.collection1,
            R.drawable.collection2,
            R.drawable.collection3
        )
        wishess = arrayOf(true, false, true)
        al2 = arrayListOf<Essentials>()
        for (i in imageList.indices) {
            val list = Essentials(imageList[i], wishess[i])
            al2.add(list)
        }
        adapter = EssentialsAdapter(al2)
        viewPager.adapter = adapter

        val indicator = binding.exploreci
        indicator.setViewPager(viewPager)


    }



}