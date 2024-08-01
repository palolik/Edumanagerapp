package com.kotlin5.edumanager.presentation.courses.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin5.edumanager.data.CourseModel
import com.kotlin5.edumanager.presentation.courses.model.Instance
import com.kotlin5.edumanager.presentation.courses.model.ServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel: ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<CourseModel>?>

    init {
        liveDataList = MutableLiveData()
    }


    fun getLiveDataObserver(): MutableLiveData<List<CourseModel>?> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = Instance.getInstance()
        val retroService  = retroInstance.create(ServiceInterface::class.java)
        val call  = retroService.getCourseList()
        call.enqueue(object : Callback<List<CourseModel>> {
            override fun onFailure(call: Call<List<CourseModel>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<CourseModel>>,
                response: Response<List<CourseModel>>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }
}