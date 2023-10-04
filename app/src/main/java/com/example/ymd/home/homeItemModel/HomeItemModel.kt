package com.example.ymd.home.homeItemModel

data class HomeItemModel(
    val thumbnail: String,
    val title: String,
    val descriptor: String,
    val id: String
){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}
