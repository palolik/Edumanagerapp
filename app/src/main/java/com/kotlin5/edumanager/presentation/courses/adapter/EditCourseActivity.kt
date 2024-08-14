package com.kotlin5.edumanager.presentation.courses.adapter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.CourseDetailsResponse
import com.kotlin5.edumanager.presentation.courses.model.Instance
import com.kotlin5.edumanager.databinding.ActivityEditCourseBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCourseBinding
    private var courseId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        courseId = intent.getStringExtra("id")

        if (courseId != null) {
            fetchCourseDetails(courseId!!)
        } else {
            Toast.makeText(this, "No Course ID provided", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnSave.setOnClickListener {
            courseId?.let {
                updateCourse(it)
            }
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
                        binding.etCourseName.setText(courseDetails.title)
                        binding.etDescription.setText(courseDetails.description)
                        binding.etStatus.setText(courseDetails.status)
                        binding.etPrice.setText(courseDetails.price)
                        binding.etRegion.setText(courseDetails.totalenrolment)
                        binding.etInstructor.setText(courseDetails.userName)
                        binding.etContact.setText(courseDetails.userEmail)
                        binding.etImage.setText(courseDetails.image)

                    }
                }
            }

            override fun onFailure(call: Call<CourseDetailsResponse>, t: Throwable) {
                Toast.makeText(this@EditCourseActivity, "Failed to fetch course details: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateCourse(id: String) {
        val updatedCourse = CourseDetailsResponse(
            title = binding.etCourseName.text.toString(),
            description = binding.etDescription.text.toString(),
            status = binding.etStatus.text.toString(),
            price = binding.etPrice.text.toString(),
            totalenrolment = binding.etRegion.text.toString(),
            userName = binding.etInstructor.text.toString(),
            userEmail = binding.etContact.text.toString(),
            image = binding.etImage.text.toString()

        )

        Instance.apiService.updateCourse(id, updatedCourse).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditCourseActivity, "Course updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditCourseActivity, "Failed to update course: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditCourseActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
