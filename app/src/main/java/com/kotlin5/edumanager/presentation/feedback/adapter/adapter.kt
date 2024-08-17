package com.kotlin5.edumanager.presentation.courses.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.Feedback
import com.kotlin5.edumanager.databinding.ItemFeedbackBinding

class FeedbackList(private val activity: Activity) : RecyclerView.Adapter<FeedbackList.MyViewHolder>() {

    private var feedbackList: List<Feedback> = listOf()

    fun setFeedbackList(feedbackList: List<Feedback>) {
        this.feedbackList = feedbackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        feedbackList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = feedbackList.size

    inner class MyViewHolder(private val binding: ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feedback: Feedback) {
            binding.textViewFeedback.text = feedback.feedback
            binding.textViewUserEmail.text = feedback.userEmail

            val stars = listOf(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5
            )

            for (i in stars.indices) {
                stars[i].setImageResource(
                    if (feedback.rating > i.toString()) R.drawable.ic_star_filled
                    else R.drawable.ic_star_empty
                )
            }
        }
    }
}
