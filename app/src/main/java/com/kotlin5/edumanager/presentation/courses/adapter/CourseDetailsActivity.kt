package com.kotlin5.edumanager.presentation.courses.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.data.CourseDetailsResponse
import com.kotlin5.edumanager.presentation.courses.model.Instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class CourseDetailsActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var textViewRegion: TextView
    private lateinit var textViewInstructor: TextView
    private lateinit var textViewContact: TextView
    private lateinit var imageViewFlag: ImageView
    private lateinit var enrollNow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        // Initialize TextViews and ImageView
        textViewName = findViewById(R.id.tvCoursename)
        textViewDescription = findViewById(R.id.tvdescription)
        textViewStatus = findViewById(R.id.tvstatus)
        textViewPrice = findViewById(R.id.tvprice)
        textViewRegion = findViewById(R.id.tvRegion2)
        textViewInstructor = findViewById(R.id.tvinstructor)
        textViewContact = findViewById(R.id.contact)
        imageViewFlag = findViewById(R.id.flagImage)
        enrollNow = findViewById(R.id.enrollnow)

        // Retrieve the Intent that started this Activity
        val intent = intent
        val id = intent.getStringExtra("id")

        if (id != null) {
            fetchCourseDetails(id)
        } else {
            // Handle the case where no ID is found
            textViewName.text = "No Course ID provided"
            textViewDescription.text = ""
            textViewStatus.text = ""
            textViewPrice.text = ""
            textViewRegion.text = ""
            textViewInstructor.text = ""
            textViewContact.text = ""
            imageViewFlag.setImageResource(R.drawable.s1) // Set a placeholder image
        }
    }

    private fun fetchCourseDetails(id: String) {
        // Use the ApiService from the Instance class
        Instance.apiService.getCourseDetails(id).enqueue(object : Callback<CourseDetailsResponse> {
            override fun onResponse(
                call: Call<CourseDetailsResponse>,
                response: Response<CourseDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    val courseDetails = response.body()
                    if (courseDetails != null) {
                        textViewName.text = courseDetails.title
                        textViewDescription.text = courseDetails.description
                        textViewStatus.text = courseDetails.status
                        textViewPrice.text = courseDetails.price
                        textViewRegion.text = courseDetails.totalenrolment
                        textViewInstructor.text = courseDetails.userName
                        textViewContact.text = courseDetails.userEmail
                        // Load image
                        loadImageFromUrl(courseDetails.image)
                    }
                }
            }

            override fun onFailure(call: Call<CourseDetailsResponse>, t: Throwable) {
                // Handle failure
                textViewName.text = "Request failed: ${t.message}"
                textViewDescription.text = ""
                textViewStatus.text = ""
                textViewPrice.text = ""
                textViewRegion.text = ""
                textViewInstructor.text = ""
                textViewContact.text = ""
                imageViewFlag.setImageResource(R.drawable.s1)
            }
        })
    }

    private fun loadImageFromUrl(imageUrl: String) {
        // Perform the image loading in a background thread
        Thread {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.inputStream.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    runOnUiThread {
                        imageViewFlag.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    imageViewFlag.setImageResource(R.drawable.s1) // Set a placeholder image if error occurs
                }
            }
        }.start()
    }
}
