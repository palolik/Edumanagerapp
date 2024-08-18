//package com.kotlin5.edumanager.presentation.courses.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.kotlin5.edumanager.data.Faq
//import com.kotlin5.edumanager.model.ApiService
//import com.kotlin5.edumanager.model.Instance
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//class FaqViewModel: ViewModel() {
//
//    lateinit var liveDataList: MutableLiveData<List<Faq>?>
//
//    init {
//        liveDataList = MutableLiveData()
//    }
//
//
//    fun getLiveDataObserver(): MutableLiveData<List<Faq>?> {
//        return liveDataList
//    }
//
//    fun makeAPICall() {
//        val retroInstance = Instance.getInstance()
//        val retroService  = retroInstance.create(ApiService::class.java)
//        val call  = retroService.getFaqList()
//        call.enqueue(object : Callback<List<Faq>> {
//            override fun onFailure(call: Call<List<Faq>>, t: Throwable) {
//                liveDataList.postValue(null)
//            }
//
//            override fun onResponse(
//                call: Call<List<Faq>>,
//                response: Response<List<Faq>>
//            ) {
//                liveDataList.postValue(response.body())
//            }
//        })
//
//
//    }
//}