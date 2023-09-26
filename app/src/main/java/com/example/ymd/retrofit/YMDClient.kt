package com.example.ymd.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object YMDClient {
    private const val DUST_BASE_URL ="https://www.googleapis.com/youtube/v3/"

    val api: HotTopicInterface
        get() = instanse.create(HotTopicInterface::class.java)

        private val instanse: Retrofit
        private get(){
            val gson = GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(DUST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}