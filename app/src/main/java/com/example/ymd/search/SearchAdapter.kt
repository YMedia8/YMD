package com.example.ymd.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.ymd.MainActivity
import com.example.ymd.databinding.SearchImageBinding
import com.example.ymd.datail.DetailActivity

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
        holder.dateTime.text=items[position].dateTime

    }
    inner class SearchItemViewHolder(binding : SearchImageBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        var thumbnail=binding.searchVideo
        var videoName=binding.videoName
        var searchView=binding.root
        var dateTime=binding.dateTime

        init{
            searchView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position=adapterPosition.takeIf { it!=RecyclerView.NO_POSITION } ?: return
            val intent = Intent(sContext, DetailActivity::class.java)
            intent.putExtra("thumbNailUrl",items[position].url)
            intent.putExtra("title",items[position].title)
            intent.putExtra("date",items[position].dateTime)
            sContext.startActivity(intent)
            sContext as MainActivity
            sContext.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

    }
}