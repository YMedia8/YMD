package com.example.ymd.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.databinding.SearchImageBinding

class SearchAdapter(private val sContext : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items=ArrayList<SearchItemModel>()
    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding=SearchImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(sContext)
            .load(items[position].url)
            .into((holder as SearchItemViewHolder).thumbnail)
        holder.videoName.text=items[position].title

    }
    inner class SearchItemViewHolder(binding : SearchImageBinding) : RecyclerView.ViewHolder(binding.root){
        var thumbnail=binding.searchVideo
        var videoName=binding.videoName
    }
}