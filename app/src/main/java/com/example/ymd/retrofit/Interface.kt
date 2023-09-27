package com.example.ymd.retrofit

import com.example.ymd.retrofit.Categories.Categories
import com.example.ymd.retrofit.Search.Search
import com.example.ymd.retrofit.YoutubeData.VideoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Interface {

    @GET("videos")
    suspend fun video (
        @Query("key") key : String,
        @Query("part") part : String?,
        @Query("chart") chart :String?,
        @Query("maxResults") maxResults: Int = 10,
    ):  Call<VideoData?>


    @GET("videoCategories")
    suspend fun category (
        @Query("key") key : String,
        @Query("part") part : String?,
        @Query("id") id : String?,
        @Query("regionCode") regionCode : String?,
    ): Call<Categories?>


    @GET("search")
    fun search (
        @Query("regionCode") regionCode: String,
        @Query("key") key : String,
        @Query("q") query: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 25,
    ): Call<Search?>
}