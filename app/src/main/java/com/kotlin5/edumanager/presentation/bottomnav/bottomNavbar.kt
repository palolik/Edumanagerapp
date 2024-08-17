package com.kotlin5.edumanager.presentation.courses

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.presentation.courses.adapter.AddCourseActivity
import com.kotlin5.edumanager.presentation.slides.model.SliderActivity

class BottomNavController(private val context: Context) {

    fun setupBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already in Home
                    true
                }
                R.id.nav_dashboard -> {
                    context.startActivity(Intent(context, AddCourseActivity::class.java))
                    true
                }
                R.id.nav_notifications -> {
                    context.startActivity(Intent(context, SliderActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
