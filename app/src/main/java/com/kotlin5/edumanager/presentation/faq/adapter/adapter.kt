//package com.kotlin5.edumanager.presentation.courses.adapter
//
//import android.app.Activity
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import coil.decode.SvgDecoder
//import coil.load
//import com.kotlin5.edumanager.data.CourseModel
//import com.kotlin5.edumanager.data.Faq
//import com.kotlin5.edumanager.databinding.ItemFaqBinding
//import com.kotlin5.edumanager.presentation.courses.adapter.CourseDetailsActivity
//
//class FaqList(private val activity: Activity) : RecyclerView.Adapter<FaqList.MyViewHolder>() {
//
//    private var FaqList: List<Faq> = listOf()
//
//    fun setFaqList(FaqList: List<Faq>) {
//        this.FaqList = FaqList
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ItemFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        FaqList.getOrNull(position)?.let { holder.bind(it) }
//    }
//
//    override fun getItemCount(): Int = FaqList.size
//
//    inner class MyViewHolder(private val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(Faq: Faq) {
//            binding.textViewCaption.text = "${Faq.caption}"
//            binding.textViewImage.load(Faq.logo) {
//                decoderFactory(SvgDecoder.Factory())
//                crossfade(true)
//            }
//
//        }
//    }
//}
