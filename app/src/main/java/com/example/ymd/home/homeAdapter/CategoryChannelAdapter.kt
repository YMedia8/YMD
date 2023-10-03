package com.example.ymd.home.homeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.databinding.ItemRecyclerViewListBinding
import com.example.ymd.home.homeItemModel.CategoryChannelItemModel
import com.example.ymd.home.homeItemModel.CategoryVideoItemModel

class CategoryChannelAdapter (private var categoryChannelList: MutableList<CategoryChannelItemModel>) : RecyclerView.Adapter<CategoryChannelAdapter.Holder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryChannelAdapter.Holder {
        val binding = ItemRecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: CategoryChannelAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val videoUrl = categoryChannelList[position].thumbnail
        Glide.with(holder.video.context)
            .load(videoUrl)
            .into(holder.video)
        holder.title.text = categoryChannelList[position].title
    }

    override fun getItemCount(): Int {
        return categoryChannelList.size
    }

    inner class Holder(binding: ItemRecyclerViewListBinding) : RecyclerView.ViewHolder(binding.root){
        var video = binding.imageView
        val title = binding.title
    }

    fun updateData(newData: List<CategoryChannelItemModel>){
        categoryChannelList = newData.toMutableList()
        notifyDataSetChanged()
    }
}