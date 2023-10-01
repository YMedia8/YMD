package com.example.ymd.home.homeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ymd.home.homeItemModel.CategoryItemModel

class CategoryViewModel : ViewModel() {
    val categoryList: MutableLiveData<List<CategoryItemModel>> by lazy {
        MutableLiveData<List<CategoryItemModel>>(emptyList())
    }

    fun category(list: MutableList<CategoryItemModel>){
        val currentList = categoryList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        categoryList.value = currentList
    }
}