package com.kotlin5.edumanager.model

import com.kotlin5.edumanager.data.Course
import com.kotlin5.edumanager.data.CourseDetailsResponse
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.data.Feedback
import com.kotlin5.edumanager.data.Partner
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiceInterface {

    @GET("classes")
    fun getCourseList(): Call<List<CourseModel>>
}

interface ApiService {
    @POST("addclasses")
    fun addCourse(@Body course: Course): Call<Void>

    @GET("classes/{id}")
    fun getCourseDetails(@Path("id") id: String): Call<CourseDetailsResponse>

    @DELETE("delPost/{id}")
    fun deleteCourse(@Path("id") id: String): Call<Void>

    @PUT("classes/{id}")
    fun updateCourse(@Path("id") id: String, @Body course: CourseDetailsResponse): Call<Void>

    @GET("feedback")
    fun getFeedbackList(): Call<List<Feedback>>
    @GET("partners")
    fun getPartnerList(): Call<List<Partner>>


}



