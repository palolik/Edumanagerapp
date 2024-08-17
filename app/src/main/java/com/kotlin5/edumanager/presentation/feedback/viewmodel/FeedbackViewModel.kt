package com.kotlin5.edumanager.presentation.courses.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin5.edumanager.data.Feedback
import com.kotlin5.edumanager.model.ApiService
import com.kotlin5.edumanager.model.Instance
import com.kotlin5.edumanager.model.ServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedbackViewModel: ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<Feedback>?>

    init {
        liveDataList = MutableLiveData()
    }
    fun getLiveDataObserver(): MutableLiveData<List<Feedback>?> {
        return liveDataList
    }
    fun makeAPICall() {
        val retroInstance = Instance.getInstance()
        val retroService  = retroInstance.create(ApiService::class.java)
        val call  = retroService.getFeedbackList()
        call.enqueue(object : Callback<List<Feedback>> {
            override fun onFailure(call: Call<List<Feedback>>, t: Throwable) {
                liveDataList.postValue(null)
            }
            override fun onResponse(
                call: Call<List<Feedback>>,
                response: Response<List<Feedback>>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }
}