package com.kotlin5.edumanager.presentation.courses.adapter

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.Course
import com.kotlin5.edumanager.databinding.ActivityAddCourseBinding
import com.kotlin5.edumanager.presentation.courses.DrawerManager
import com.kotlin5.edumanager.presentation.courses.viewmodel.CourseViewModel

class AddCourseActivity : AppCompatActivity() {
    private lateinit var drawerManager: DrawerManager
    private lateinit var binding: ActivityAddCourseBinding
    private lateinit var titleField: TextInputEditText
    private lateinit var courseDesField: TextInputEditText
    private lateinit var imagelinkField: TextInputEditText
    private lateinit var priceField: TextInputEditText
    private lateinit var useremailField: TextInputEditText
    private lateinit var usernameField: TextInputEditText
    private lateinit var totalenrollmentField: TextInputEditText
    private lateinit var statusField: TextInputEditText

    private val courseViewModel: CourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI elements
        titleField = findViewById(R.id.titleField)
        courseDesField = findViewById(R.id.CourseDesField)
        imagelinkField = findViewById(R.id.imagelinkField)
        priceField = findViewById(R.id.priceField)
        useremailField = findViewById(R.id.useremailField)
        usernameField = findViewById(R.id.usernameField)
        totalenrollmentField = findViewById(R.id.totalenrollmentField)
        statusField = findViewById(R.id.statusField)

        // Initialize DrawerManager
        initDrawerManager()

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

        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Title and Description are required", Toast.LENGTH_SHORT).show()
        } else {
            val course = Course(title, description, image, price, userEmail, userName, totalenrolment, status)
            courseViewModel.addCourse(course) { success, errorMessage ->
                if (success) {
                    Toast.makeText(this, "Course saved successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save course: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return drawerManager.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}
