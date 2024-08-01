package com.kotlin5.edumanager.presentation.courses.model

import com.kotlin5.edumanager.data.CourseModel
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {

    @GET("classes")
    fun getCourseList(): Call<List<CourseModel>>
}