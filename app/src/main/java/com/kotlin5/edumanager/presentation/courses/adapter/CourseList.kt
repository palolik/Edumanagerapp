package com.kotlin5.edumanager.presentation.courses.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.databinding.CourseListRowBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseDetailsActivity

class CourseList(private val activity: Activity) : RecyclerView.Adapter<CourseList.MyViewHolder>() {

    private var courseList: List<CourseModel> = listOf()

    fun setCourseList(courseList: List<CourseModel>) {
        this.courseList = courseList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CourseListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        courseList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = courseList.size

    inner class MyViewHolder(private val binding: CourseListRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CourseModel) {
            binding.tvName.text = "${data.title}"
            binding.tvCapital.text = "${data.description}"
            binding.tvRegion.text = "Status: ${data.status}"
            binding.tvRegion2.text = "Price: ${data.price}"
            binding.viewdetails.text = "View Details"
            binding.flagImage.load(data.image) {
                decoderFactory(SvgDecoder.Factory())
                crossfade(true)
            }

            binding.viewdetails.setOnClickListener {
                val intent = Intent(activity, CourseDetailsActivity::class.java).apply {
                    putExtra("id", data._id) // Replace with your actual data field
                }
                // Start the new activity
                activity.startActivity(intent)
            }
        }
    }
}
