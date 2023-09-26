package com.example.ymd.retrofit

import com.example.ymd.retrofit.HotTopicData.MediaData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface HotTopicInterface {
    @GET("videos")
    suspend fun HotTopic(
        @Query("part") part : String?,
        @Query("chart") chart :String?,
        @Query("id") id : String?,
        @Query("myRating") myRating : String?
    ):  Call<MediaData?>
}