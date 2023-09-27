package com.example.ymd.retrofit

import com.example.ymd.retrofit.Categories.Categories
import com.example.ymd.retrofit.Search.Search
import com.example.ymd.retrofit.YoutubeData.VideoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Interface {

    @GET("videos?key = ${Constans.AUTH_HEADER}")
    suspend fun video (
        @Query("part") part : String?,
        @Query("chart") chart :String?,
        @Query("maxResults") maxResults: Int = 10,
    ):  Call<VideoData?>


    @GET("videoCategories?key = ${Constans.AUTH_HEADER}")
    suspend fun category (
        @Query("part") part : String?,
        @Query("id") id : String?,
        @Query("regionCode") regionCode : String?,
    ): Call<Categories?>


    @GET("search?key = ${Constans.AUTH_HEADER}")
    suspend fun search (
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 25,
        @Query("query") query: String,
    ): Call<Search?>
}