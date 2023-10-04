package com.example.ymd.datail

data class DetailModel (
    val thumbnail: String,
    val title: String,
    val id : String,
    val channelId : String
    ){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}