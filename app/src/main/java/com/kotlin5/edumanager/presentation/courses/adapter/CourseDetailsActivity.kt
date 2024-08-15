package com.kotlin5.edumanager.presentation.courses.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.CourseDetailsResponse
import com.kotlin5.edumanager.model.Instance
import com.kotlin5.edumanager.databinding.ActivityCourseDetailsBinding
import com.kotlin5.edumanager.presentation.courses.DrawerManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL

class CourseDetailsActivity : AppCompatActivity() {
    private lateinit var drawerManager: DrawerManager

    private lateinit var binding: ActivityCourseDetailsBinding
    private var courseId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseId = intent.getStringExtra("id")
        @SuppressLint("SetTextI18n")
        if (courseId != null) {
            fetchCourseDetails(courseId!!)
        } else {
            binding.tvCoursename.text = "No Course ID provided"
            binding.tvdescription.text = ""
            binding.tvstatus.text = ""
            binding.tvprice.text = ""
            binding.tvRegion2.text = ""
            binding.tvinstructor.text = ""
            binding.contact.text = ""
            binding.flagImage.setImageResource(R.drawable.s1)
        }
        initDrawerManager()

        binding.deletecourse.setOnClickListener {
            courseId?.let {
                deleteCourse(it)
            }
        }
        binding.editcourse.setOnClickListener {
            val intent = Intent(this, EditCourseActivity::class.java).apply {
                putExtra("id", courseId)
            }
            // Start the new activity
            startActivity(intent)
        }
    }

    private fun fetchCourseDetails(id: String) {
        Instance.apiService.getCourseDetails(id).enqueue(object : Callback<CourseDetailsResponse> {
            override fun onResponse(
                call: Call<CourseDetailsResponse>,
                response: Response<CourseDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val courseDetails = response.body()
                    if (courseDetails != null) {
                        binding.tvCoursename.text = courseDetails.title
                        binding.tvdescription.text = courseDetails.description
                        binding.tvstatus.text = courseDetails.status
                        binding.tvprice.text = courseDetails.price
                        binding.tvRegion2.text = courseDetails.totalenrolment.toString()
                        binding.tvinstructor.text = courseDetails.userName
                        binding.contact.text = courseDetails.userEmail
                        loadImageFromUrl(courseDetails.image)
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<CourseDetailsResponse>, t: Throwable) {
                // Handle failure
                binding.tvCoursename.text = "Request failed: ${t.message}"
                binding.tvdescription.text = ""
                binding.tvstatus.text = ""
                binding.tvprice.text = ""
                binding.tvRegion2.text = ""
                binding.tvinstructor.text = ""
                binding.contact.text = ""
                binding.flagImage.setImageResource(R.drawable.s1)
            }
        })
    }

    private fun deleteCourse(id: String) {
        // Call the API to delete the course
        Instance.apiService.deleteCourse(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CourseDetailsActivity, "Course deleted successfully", Toast.LENGTH_SHORT).show()
                    // Finish the activity and return to the previous screen
                    finish()
                } else {
                    Toast.makeText(this@CourseDetailsActivity, "Failed to delete course: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@CourseDetailsActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadImageFromUrl(imageUrl: String) {
        Thread {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.inputStream.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    runOnUiThread {
                        binding.flagImage.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    binding.flagImage.setImageResource(R.drawable.s1) // Set a placeholder image if error occurs
                }
            }
        }.start()
    }

    private fun initDrawerManager() {

        drawerManager = DrawerManager(
            this,
            binding.drawerLayout,
            binding.toolbarhome,
            binding.navView
        )
        drawerManager.setupNavigationDrawer()
    }
}
