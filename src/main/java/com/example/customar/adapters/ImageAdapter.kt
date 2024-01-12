package com.example.customar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.customar.R

class ImageAdapter(
    private val imageList: ArrayList<Int>,
    private val viewPager2: ViewPager2,
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    lateinit var mlistener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position:Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){

        mlistener = listener

    }

    class ImageViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageSlide)

        init{
            imageView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homepagercontainer, parent, false)
        return ImageViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if (position == imageList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}
/* viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        layoutParams = viewPager.layoutParams
        layoutParams.width =1400
        viewPager.layoutParams = layoutParams*/
