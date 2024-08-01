package com.kotlin5.edumanager.presentation.splash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.presentation.splash.model.SliderData

class SliderAdapter (
    val context: Context,
    private val sliderList: ArrayList<SliderData>

): PagerAdapter() {
    override fun getCount(): Int {
        return sliderList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout

    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slider_item, container, false)
//        val imageView: ImageView = view.findViewById(R.id.idIVSlider)
        val sliderHeadingTV: TextView = view.findViewById(R.id.red_message_tv)
        val sliderDescTV: TextView = view.findViewById(R.id.green_message_tv)

        val sliderData: SliderData = sliderList[position]
        sliderHeadingTV.text = sliderData.slideTitle
        sliderDescTV.text = sliderData.slideDescription
//        imageView.setImageResource(sliderData.slideImage)
        container.addView(view)
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}