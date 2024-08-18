package com.kotlin5.edumanager.presentation.admin

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin5.edumanager.databinding.ActivityAdminBinding
import com.kotlin5.edumanager.presentation.admin.adapter.CourseList
import com.kotlin5.edumanager.presentation.admin.viewmodel.MainReviewActivityViewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: MainReviewActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCourseRecyclerView()
        initCourseViewModel()

        // Call the API to load data
        viewModel.makeAPICall()
    }

    private fun initCourseRecyclerView() {
        binding.CourseListReviewview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerAdapter = CourseList(this)
        binding.CourseListReviewview.adapter = recyclerAdapter
    }

    private fun initCourseViewModel() {
        viewModel = ViewModelProvider(this).get(MainReviewActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer { courseList ->
            if (courseList != null) {
                recyclerAdapter.setCourseList(courseList)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
