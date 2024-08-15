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
import com.kotlin5.edumanager.databinding.ActivityCourseBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseList
import com.kotlin5.edumanager.presentation.courses.adapter.FeedbackList
import com.kotlin5.edumanager.presentation.courses.adapter.PartnerList
import com.kotlin5.edumanager.presentation.courses.viewmodel.FeedbackViewModel
import com.kotlin5.edumanager.presentation.courses.viewmodel.MainActivityViewModel
import com.kotlin5.edumanager.presentation.courses.viewmodel.PartnerViewModel

class CourseActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: CourseList
    private lateinit var feedbackrAdapter: FeedbackList
    private lateinit var partnerrAdapter: PartnerList

    private lateinit var binding: ActivityCourseBinding
    private lateinit var drawerManager: DrawerManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFeedback: FeedbackViewModel
    private lateinit var viewModelPartner: PartnerViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarhome)
        initCourseRecyclerView()
        initFeedbackRecyclerView()
        initPartnerRecyclerView()
        initCourseViewModel()
        initFeedbackViewModel()
        initPartnerViewModel()
        initDrawerManager()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.makeAPICall()
        }
    }

    private fun initCourseRecyclerView() {
        binding.CourseListRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerAdapter = CourseList(this)
        binding.CourseListRecyclerview.adapter = recyclerAdapter
    }

    private fun initFeedbackRecyclerView() {
        binding.FeedbackList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        feedbackrAdapter = FeedbackList(this)
        binding.FeedbackList.adapter = feedbackrAdapter
    }
    private fun initPartnerRecyclerView() {
        binding.PartnerList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        partnerrAdapter = PartnerList(this)
        binding.PartnerList.adapter = partnerrAdapter
    }


    private fun initCourseViewModel() {
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
    private fun initFeedbackViewModel() {
        viewModelFeedback = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        viewModelFeedback.getLiveDataObserver().observe(this, Observer { feedbackList ->
            swipeRefreshLayout.isRefreshing = false
            if (feedbackList != null) {
                feedbackrAdapter.setFeedbackList(feedbackList)
                feedbackrAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting feedback list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelFeedback.makeAPICall()
    }
    private fun initPartnerViewModel() {
        viewModelPartner = ViewModelProvider(this).get(PartnerViewModel::class.java)
        viewModelPartner.getLiveDataObserver().observe(this, Observer { partnerList ->
            swipeRefreshLayout.isRefreshing = false
            if (partnerList != null) {
                partnerrAdapter.setPartnerList(partnerList)
                partnerrAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting feedback list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelPartner.makeAPICall()
    }

    private fun initDrawerManager() {
        drawerManager = DrawerManager(this, binding.drawerLayout, binding.toolbarhome, binding.navView)
        drawerManager.setupNavigationDrawer()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
//    fun onFabClick(view: View) {
//        val intent = Intent(this, AddCourseActivity::class.java)
//        startActivity(intent)
//    }


}
