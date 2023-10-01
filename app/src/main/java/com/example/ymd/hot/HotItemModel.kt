package com.example.ymd.hot

data class HotItemModel(
    val description: String,
    val title: String,
    val thumbnail: String,
    val id: String,
    val favorites: Boolean = false
)