package com.kotlin5.edumanager.presentation.courses.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instance {

    companion object {
        val BASE_URL = "https://server12-chi.vercel.app/"//v2/

        fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}