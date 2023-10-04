package com.example.ymd.home.homeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ymd.home.homeItemModel.CategoryChannelItemModel

class CategoryChannelViewModel : ViewModel() {
    val categoryChannelList = MutableLiveData<List<CategoryChannelItemModel>>()

    fun categoryChannel(list: MutableList<CategoryChannelItemModel>){
        val currentList = categoryChannelList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        categoryChannelList.value = currentList
    }

    fun clearCategoryChannel(){
        categoryChannelList.value = null
    }
}