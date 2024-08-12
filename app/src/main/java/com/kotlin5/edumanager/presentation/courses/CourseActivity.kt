package com.kotlin5.edumanager.presentation.courses

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.databinding.ActivityCourseBinding
import com.kotlin5.edumanager.presentation.auth.SignInActivity
import com.kotlin5.edumanager.presentation.courses.adapter.CourseList
import com.kotlin5.edumanager.presentation.courses.viewmodel.MainActivityViewModel

class CourseActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityCourseBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarhome)
        initRecyclerView()
        initViewModel()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.makeAPICall()
        }

        // Initialize Navigation Drawer
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarhome,  // Pass the toolbar here
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.first -> {
                    Toast.makeText(this, "First Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.second -> {
                    Toast.makeText(this, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.third -> {
                    Toast.makeText(this, "Third Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    logout()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
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
private fun logout(){
    FirebaseAuth.getInstance().signOut()
    val intent = Intent(this,SignInActivity::class.java)
    startActivity(intent)
    finish()
}
    fun onFabClick(view: View) {
        val intent = Intent(this, AddCourseActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
