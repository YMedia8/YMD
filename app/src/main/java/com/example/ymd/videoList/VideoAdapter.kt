package com.example.ymd.videoList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ymd.databinding.ItemRecyclerViewList2Binding

class VideoAdapter() : RecyclerView.Adapter<VideoAdapter.Holder> {

    interface ItemCLick{
        fun onCLick(view: View, position: Int)
    }

    var itemCLick: ItemCLick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.Holder {
        val binding = ItemRecyclerViewList2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: VideoAdapter.Holder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class Holder(binding: ItemRecyclerViewList2Binding) : RecyclerView.ViewHolder(binding.root){

    }
}