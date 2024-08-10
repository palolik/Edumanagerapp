package com.kotlin5.edumanager.presentation.courses.model

import com.kotlin5.edumanager.data.Course
import com.kotlin5.edumanager.data.CourseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceInterface {

    @GET("classes")
    fun getCourseList(): Call<List<CourseModel>>
}

interface ApiService {
    @POST("courses") // Replace with your actual API endpoint
    fun addCourse(@Body course: Course): Call<Void>
}