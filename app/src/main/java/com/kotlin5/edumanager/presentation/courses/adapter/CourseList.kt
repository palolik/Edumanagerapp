package com.kotlin5.edumanager.presentation.courses.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.databinding.CourseListRowBinding



class CourseList (private val activity: Activity) : RecyclerView.Adapter<CourseList.MyViewHolder>() {

    private var CourseList: List<CourseModel> = listOf()

    fun setCourseList(CourseList: List<CourseModel>) {
        this.CourseList = CourseList
        notifyDataSetChanged() // Notify adapter of data changes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CourseListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        CourseList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = CourseList.size

    class MyViewHolder(private val binding: CourseListRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CourseModel) {
            binding.tvName.text = "${data.title} "
            binding.tvCapital.text = "Capddital: ${data.description}"
            binding.tvRegion.text = "Region: ${data.status}"
            binding.tvRegion2.text = "Region: ${data.price}"
            binding.flagImage.load(data.image) {
                decoderFactory(SvgDecoder.Factory())
                crossfade(true)
            }
        }
    }
}
