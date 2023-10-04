package com.example.ymd.datail

data class DetailModel (
    val title: String,
    val id : String,
    ){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}