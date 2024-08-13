package com.kotlin5.edumanager.presentation.courses

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.databinding.ActivityCourseBinding
import com.kotlin5.edumanager.presentation.courses.adapter.AddCourseActivity
import com.kotlin5.edumanager.presentation.courses.adapter.CourseList
import com.kotlin5.edumanager.presentation.courses.viewmodel.MainActivityViewModel

class CourseActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityCourseBinding
    private lateinit var drawerManager: DrawerManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarhome)
        initRecyclerView()
        initViewModel()
        initDrawerManager()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.makeAPICall()
        }
    }

    private fun initRecyclerView() {
        binding.CourseListRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerAdapter = CourseList(this)
        binding.CourseListRecyclerview.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer { courseList ->
            swipeRefreshLayout.isRefreshing = false
            if (courseList != null) {
                recyclerAdapter.setCourseList(courseList)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }

    private fun initDrawerManager() {
        drawerManager = DrawerManager(this, binding.drawerLayout, binding.toolbarhome, binding.navView)
        drawerManager.setupNavigationDrawer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
    fun onFabClick(view: View) {
        val intent = Intent(this, AddCourseActivity::class.java)
        startActivity(intent)
    }


}
