package com.example.ymd.home.homeItemModel

data class HomeItemModel(
    val thumbnail: String,
    val title: String,
    val descriptor: String,
    val id: String,
    var view: Boolean
){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}
