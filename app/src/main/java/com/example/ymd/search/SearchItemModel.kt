package com.example.ymd.search

import com.google.type.DateTime

data class SearchItemModel (
    var title : String,
    var url : String,
    var dateTime : String,
    var id : String
){
    fun getVideoUrl(): String {
        return "https://www.youtube.com/embed/$id"
    }
}


