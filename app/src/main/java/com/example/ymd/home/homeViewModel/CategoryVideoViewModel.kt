package com.example.ymd.home.homeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ymd.home.homeItemModel.CategoryVideoItemModel

class CategoryVideoViewModel: ViewModel() {
    val categoryVideoList = MutableLiveData<List<CategoryVideoItemModel>?>()

    fun categoryVideo(list: MutableList<CategoryVideoItemModel>){
        val currentList = categoryVideoList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        categoryVideoList.value = currentList
    }

    fun clearCategoryVideo(){
        categoryVideoList.value = null
    }
}