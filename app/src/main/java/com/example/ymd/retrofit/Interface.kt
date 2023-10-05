package com.example.ymd.retrofit

import com.example.ymd.retrofit.categories.Categories
import com.example.ymd.retrofit.search.Search
import com.example.ymd.retrofit.youtubeData.VideoData
import com.example.ymd.retrofit.channel.Channel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Interface {
    @GET("videos")
    fun categorySearch (
        @Query("part") part : String,
        @Query("regionCode") regionCode :String,
        @Query("key") apiKey: String,
        @Query("id") id : String,
    ):  Call<VideoData?>
    @GET("videos")
    fun video (
        @Query("part") part : String,
        @Query("chart") chart :String,
        @Query("maxResults") maxResults: Int,
        @Query("regionCode") regionCode :String,
        @Query("key") apiKey: String,
        @Query("videoCategoryId") videoCategoryId : String = "0"
    ):  Call<VideoData?>


    @GET("videoCategories")
    fun category (
        @Query("key") key : String,
        @Query("part") part : String?,
        @Query("regionCode") regionCode : String?,
    ): Call<Categories?>


    @GET("search")
    fun search (
        @Query("regionCode") regionCode: String,
        @Query("key") key : String,
        @Query("q") query: String,
        @Query("videoCategoryId") videoCategoryId : String?,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 30,
        @Query("type") type : String = "video"

    ): Call<Search?>

    @GET("channels")
    fun channels(
        @Query("part") part : String,
        @Query("id") id: List<String>,
        @Query("key") key: String
    ): Call<Channel?>
}