//package com.kotlin5.edumanager
//
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.Color
//import android.os.Build
//import android.os.Bundle
//import android.text.Html
//import android.view.Gravity
//import android.view.View
//import android.view.WindowManager
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.appcompat.app.ActionBar
//import androidx.appcompat.app.AppCompatActivity
//import androidx.viewpager.widget.ViewPager
//import com.kotlin5.edumanager.presentation.sliders.MyAdapter
//
//class MainActivity : AppCompatActivity() {
//    private lateinit var viewPager: ViewPager
//    private lateinit var dotsLayout: LinearLayout
//    private lateinit var btnNext: TextView  // Use TextView for Button
//    private lateinit var btnSkip: TextView  // Use TextView for Button
//    private lateinit var myAdapter: MyAdapter
//    private val layouts = intArrayOf(R.layout.slide_1, R.layout.slide_2, R.layout.slide_3)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Initialize views before setting the content view
//        setContentView(R.layout.activity_main)
//
//        // Initialize ActionBar
//        val actionBar: ActionBar? = supportActionBar
//        actionBar?.hide()
//        statusBarTransparent()
//
//        // Initialize views
//        viewPager = findViewById(R.id.viewPager)  // Initialize ViewPager
//        dotsLayout = findViewById(R.id.dotsLayout)  // Initialize LinearLayout
//        btnNext = findViewById(R.id.btn_next)  // Initialize Next Button
//        btnSkip = findViewById(R.id.btn_prev)  // Initialize Previous Button
//
//        // Initialize Adapter
//        myAdapter = MyAdapter(layouts, applicationContext)
//        viewPager.adapter = myAdapter
//
//        // Button click listeners
//        btnNext.setOnClickListener {
//            val currentPage: Int = viewPager.currentItem
//
//            if (currentPage < layouts.size - 1) {
//                viewPager.currentItem = currentPage + 1
//            } else {
//                setAppStartStatus(true)
//                startActivity(Intent(this, AnotherActivity::class.java))  // Ensure AnotherActivity is valid
//                finish()
//            }
//        }
//
//        btnSkip.setOnClickListener {
//            setAppStartStatus(true)
//            startActivity(Intent(this, AnotherActivity::class.java))  // Ensure AnotherActivity is valid
//            finish()
//        }
//
//        // Page change listener
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {}
//
//            override fun onPageSelected(position: Int) {
//                if (position == layouts.size - 1) {
//                    btnNext.text = "START"
//                    btnSkip.visibility = View.GONE
//                } else {
//                    btnNext.text = "NEXT"
//                    btnSkip.visibility = View.VISIBLE
//                }
//                setDots(position)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {}
//        })
//
//        // Initialize dots
//        setDots(0)
//    }
//
//    private fun isFirstStartStatus(): Boolean {
//        val pref = applicationContext.getSharedPreferences("EDUMANAGER", Context.MODE_PRIVATE)
//        return pref.getBoolean("APP_START", true)
//    }
//
//    private fun setAppStartStatus(status: Boolean) {
//        val pref = applicationContext.getSharedPreferences("EDUMANAGER", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = pref.edit()
//        editor.putBoolean("APP_START", status)
//        editor.apply()
//    }
//
//    private fun statusBarTransparent() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//        }
//    }
//
//    private fun setDots(page: Int) {
//        dotsLayout.removeAllViews()
//        val dotsTv = arrayOfNulls<TextView>(layouts.size)
//        dotsLayout.gravity = Gravity.CENTER
//
//        for (i in dotsTv.indices) {
//            dotsTv[i] = TextView(this)
//            dotsTv[i]?.apply {
//                text = Html.fromHtml("&#8226;")
//                textSize = 60f
//                setTextColor(Color.parseColor("#a9b4bb"))
//                dotsLayout.addView(this)
//            }
//        }
//
//        if (dotsTv.isNotEmpty()) {
//            dotsTv[page]?.setTextColor(Color.parseColor("#ffffff"))
//        }
//    }
//}

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
