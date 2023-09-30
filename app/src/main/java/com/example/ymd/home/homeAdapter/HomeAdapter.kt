package com.example.ymd.home.homeAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.databinding.ItemRecyclerViewListBinding
import com.example.ymd.home.homeItemModel.HomeItemModel

class HomeAdapter(private var homeList: MutableList<HomeItemModel>) : RecyclerView.Adapter<HomeAdapter.Holder>(){

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.Holder {
        val binding = ItemRecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        val videoUrl = homeList[position].thumbnail
        Glide.with(holder.video.context)
            .load(videoUrl)
            .into(holder.video)
        holder.title.text = homeList[position].title
//        holder.subtitle.text = homeList[position].descriptor
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    inner class Holder(binding: ItemRecyclerViewListBinding) : RecyclerView.ViewHolder(binding.root){
        var video = binding.imageView
        val title = binding.title
//        val subtitle = binding.subTitle
    }

    fun updateData(newData: List<HomeItemModel>) {
        homeList = newData.toMutableList()
        notifyDataSetChanged()
    }

}