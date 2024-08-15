package com.kotlin5.edumanager.presentation.courses.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.decode.SvgDecoder
import coil.load
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.data.Feedback
import com.kotlin5.edumanager.databinding.ItemFeedbackBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseDetailsActivity

class FeedbackList(private val activity: Activity) : RecyclerView.Adapter<FeedbackList.MyViewHolder>() {

    private var FeedbackList: List<Feedback> = listOf()

    fun setFeedbackList(FeedbackList: List<Feedback>) {
        this.FeedbackList = FeedbackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        FeedbackList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = FeedbackList.size

    inner class MyViewHolder(private val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedback: Feedback) {
            binding.textViewFeedback.text = "${feedback.feedback}"
            binding.textViewRating.text = "${feedback.rating}"
            binding.textViewUserEmail.text = "${feedback.userEmail}"

        }
    }
}
