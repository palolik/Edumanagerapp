package com.kotlin5.edumanager.presentation.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kotlin5.edumanager.databinding.ActivityAdminBinding
import com.kotlin5.edumanager.presentation.admin.adapter.CourseList
import com.kotlin5.edumanager.presentation.admin.viewmodel.MainReviewActivityViewModel
import com.kotlin5.edumanager.presentation.auth.SignInActivity



class AdminActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: MainReviewActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCourseRecyclerView()
        initCourseViewModel()
        viewModel.makeAPICall()

        val logoutButton: Button = binding.logout
        logoutButton.setOnClickListener {
            logout()
        }
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
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, SignInActivity::class.java)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }

}
