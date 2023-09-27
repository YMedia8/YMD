package com.example.ymd.retrofit

import com.example.ymd.retrofit.Categories.Categories
import com.example.ymd.retrofit.YoutubeData.VideoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface HotTopicInterface {
    @GET("videos")
    suspend fun video (
        @Query("part") part : String?,
        @Query("chart") chart :String?,
        @Query("id") id : String?,
        @Query("myRating") myRating : String?
    ):  Call<VideoData?>
    @GET("videoCategories")
    suspend fun category (
        @Query("part") part : String?,
        @Query("id") id : String?,
        @Query("regionCode") regionCode : String?,
    ): Call<Categories?>
}