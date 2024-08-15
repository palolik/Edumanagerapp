package com.kotlin5.edumanager.presentation.courses.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.data.Partner
import com.kotlin5.edumanager.databinding.ItemPartnerBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseDetailsActivity

class PartnerList(private val activity: Activity) : RecyclerView.Adapter<PartnerList.MyViewHolder>() {

    private var PartnerList: List<Partner> = listOf()

    fun setPartnerList(PartnerList: List<Partner>) {
        this.PartnerList = PartnerList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPartnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        PartnerList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = PartnerList.size

    inner class MyViewHolder(private val binding: ItemPartnerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(Partner: Partner) {
            binding.textViewCaption.text = "${Partner.caption}"
            binding.textViewImage.load(Partner.logo) {
                decoderFactory(SvgDecoder.Factory())
                crossfade(true)
            }

        }
    }
}
