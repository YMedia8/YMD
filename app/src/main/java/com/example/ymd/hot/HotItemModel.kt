package com.example.ymd.hot

data class HotItemModel(
    val id: String,
    val title: String,
    val thumbnail: String,
    val favorites: Boolean = false
)