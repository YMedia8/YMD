package com.example.ymd.home.homeAdapter.com.example.ymd.home.homeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.databinding.ItemRecyclerViewListBinding
import com.example.ymd.home.homeItemModel.CategoryVideoItemModel

class CategoryVideoAdapter(private var categoryList: MutableList<CategoryVideoItemModel>) : RecyclerView.Adapter<CategoryVideoAdapter.Holder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVideoAdapter.Holder {
        val binding = ItemRecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: CategoryVideoAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val videoUrl = categoryList[position].thumbnail
        Glide.with(holder.video.context)
            .load(videoUrl)
            .into(holder.video)
        holder.title.text = categoryList[position].title
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class Holder(binding: ItemRecyclerViewListBinding) : RecyclerView.ViewHolder(binding.root){
        var video = binding.imageView
        val title = binding.title
    }

    fun updateData(newData: List<CategoryVideoItemModel>){
        categoryList = newData.toMutableList()
        notifyDataSetChanged()
    }
}