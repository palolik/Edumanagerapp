package com.kotlin5.edumanager.presentation.courses.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin5.edumanager.data.Course
import com.kotlin5.edumanager.presentation.courses.model.ApiService
import com.kotlin5.edumanager.presentation.courses.model.Instance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = Instance.getInstance().create(ApiService::class.java)

    fun addCourse(course: Course, onResult: (Boolean, String?) -> Unit) {
        apiService.addCourse(course).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onResult(false, t.message)
            }
        })
    }
}
