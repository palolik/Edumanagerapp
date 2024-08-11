package com.kotlin5.edumanager.presentation.courses

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.databinding.ActivityMainBinding
import com.kotlin5.edumanager.presentation.courses.adapter.CourseList
import com.kotlin5.edumanager.presentation.courses.viewmodel.MainActivityViewModel

class CourseActivity : AppCompatActivity() {
    private lateinit var recyclerAdapter: CourseList
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Toolbar
        setSupportActionBar(binding.toolbarhome)  // Ensure this matches the ID in your XML

        initRecyclerView()
        initViewModel()

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

        // Set up navigation view listener
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
            }
            binding.drawerLayout.closeDrawers() // Close drawer after selection
            true
        }

        // Ensure the home button is enabled
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
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
