package com.example.ymd.home.homeItemModel

data class CategoryVideoItemModel(
    val thumbnail: String,
    val title: String,
    val descriptor: String,
    val id : String
){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}
