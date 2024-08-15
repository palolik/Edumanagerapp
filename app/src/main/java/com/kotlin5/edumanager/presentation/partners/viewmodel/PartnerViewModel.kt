package com.kotlin5.edumanager.presentation.courses.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin5.edumanager.data.Partner
import com.kotlin5.edumanager.model.ApiService
import com.kotlin5.edumanager.model.Instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PartnerViewModel: ViewModel() {

    lateinit var liveDataList: MutableLiveData<List<Partner>?>

    init {
        liveDataList = MutableLiveData()
    }


    fun getLiveDataObserver(): MutableLiveData<List<Partner>?> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = Instance.getInstance()
        val retroService  = retroInstance.create(ApiService::class.java)
        val call  = retroService.getPartnerList()
        call.enqueue(object : Callback<List<Partner>> {
            override fun onFailure(call: Call<List<Partner>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<Partner>>,
                response: Response<List<Partner>>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }
}