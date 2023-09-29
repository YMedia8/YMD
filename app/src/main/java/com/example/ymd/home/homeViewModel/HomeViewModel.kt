package com.example.ymd.home.homeViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ymd.home.HomeItemModel

class HomeViewModel : ViewModel() {
    val homeList: MutableLiveData<List<HomeItemModel>> by lazy {
        MutableLiveData<List<HomeItemModel>>(emptyList())
    }

    fun viewList(list: MutableList<HomeItemModel>){
        var currentList = homeList.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(list)
        homeList.value = currentList
    }
}