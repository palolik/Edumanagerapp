package com.kotlin5.edumanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin5.edumanager.databinding.ActivityMainBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseList
import com.kotlin5.edumanager.presentation.courses.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initViewModel()
        }
    private fun initRecyclerView() {
        binding.CourseListRecyclerview.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = CourseList(this)
        binding.CourseListRecyclerview.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        val viewModel: MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer { courseList ->
            if (courseList != null) {
                recyclerAdapter.setCourseList(courseList)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }
}