package com.example.ymd.mypage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.ymd.MainActivity
import com.example.ymd.Utils
import com.example.ymd.databinding.MyItemBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.hot.HotItemModel

class MypageAdapter(private val MypageAdapterContext : Context) : RecyclerView.Adapter<MypageAdapter.videoViewHolder>() {

    var video = mutableListOf<HotItemModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): videoViewHolder {
        val binding = MyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return videoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return video.size
    }

    override fun onBindViewHolder(holder: videoViewHolder, position: Int) {
        Glide.with(MypageAdapterContext)
            .load(video[position].thumbnail)
            .into((holder as videoViewHolder).mypageVideo)

        holder.mypageTitel.text = video[position].title
    }

    inner class videoViewHolder(binding: MyItemBinding) : RecyclerView.ViewHolder(binding.root){
        var mypageVideo : ImageView = binding.myImage
        var mypageTitel : TextView = binding.myTitle
        var mypagePlay : ImageView = binding.playbt
        var xBtn=binding.myPageClose
        var mypageView=binding.root

        init {
            xBtn.setOnClickListener {
                val position=adapterPosition
                Utils.deletePrefItem(MypageAdapterContext,video[position].thumbnail)
                if (position != RecyclerView.NO_POSITION) {
                    video.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }


//        init {
//            mypagePlay.setOnClickListener{
//                val intent = Intent(MypageAdapterContext,DetailActivity::class.java)
//                MypageAdapterContext.startActivity(intent)
//                Log.d("MypageAdapter", "화면이동")
//            }
//        }

    }
}