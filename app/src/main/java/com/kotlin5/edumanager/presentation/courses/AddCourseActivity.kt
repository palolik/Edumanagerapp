package com.kotlin5.edumanager.presentation.courses

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.Course
import com.kotlin5.edumanager.presentation.courses.model.ApiService
import com.kotlin5.edumanager.presentation.courses.model.Instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCourseActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var titleField: TextInputEditText
    private lateinit var courseDesField: TextInputEditText
    private lateinit var imagelinkField: TextInputEditText
    private lateinit var priceField: TextInputEditText
    private lateinit var useremailField: TextInputEditText
    private lateinit var usernameField: TextInputEditText
    private lateinit var totalenrollmentField: TextInputEditText
    private lateinit var statusField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        // Initialize UI elements
        toolbar = findViewById(R.id.toolbar)
        titleField = findViewById(R.id.titleField)
        courseDesField = findViewById(R.id.CourseDesField)
        imagelinkField = findViewById(R.id.imagelinkField)
        priceField = findViewById(R.id.priceField)
        useremailField = findViewById(R.id.useremailField)
        usernameField = findViewById(R.id.usernameField)
        totalenrollmentField = findViewById(R.id.totalenrollmentField)
        statusField = findViewById(R.id.statusField)

        // Set up the toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val saveButton: Button = findViewById(R.id.addcourse)
        saveButton.setOnClickListener { saveCourse() }
    }

    private fun saveCourse() {
        // Retrieve input values
        val title = titleField.text.toString()
        val description = courseDesField.text.toString()
        val image = imagelinkField.text.toString()
        val price = priceField.text.toString()
        val userEmail = useremailField.text.toString()
        val userName = usernameField.text.toString()
        val totalenrolment = totalenrollmentField.text.toString()
        val status = statusField.text.toString()

        // Validate input
        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Title and Description are required", Toast.LENGTH_SHORT).show()
        } else {
            // Create Course object and make network request
            val course = Course(title, description, image, price, userEmail, userName, totalenrolment, status)
            val retrofit = Instance.getInstance()
            val apiService = retrofit.create(ApiService::class.java)
            apiService.addCourse(course).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddCourseActivity, "Course saved successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AddCourseActivity, "Failed to save course: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                        Log.e("AddCourseActivity", "Failed to save course: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@AddCourseActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

