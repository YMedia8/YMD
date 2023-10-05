package com.example.ymd.home.homeAdapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ymd.R
import com.example.ymd.databinding.ItemRecyclerViewListBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.home.homeItemModel.HomeItemModel

class HomeAdapter(private var homeList: MutableList<HomeItemModel>) : RecyclerView.Adapter<HomeAdapter.Holder>(){

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    private var isWebViewVisible = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.Holder {
        val binding = ItemRecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
//        val videoUrl = homeList[position].thumbnail
//        Glide.with(holder.video.context)
//            .load(videoUrl)
//            .into(holder.video)
        val url = homeList[position].getVideoUrl()
        if (url.isNotEmpty()){
            isWebViewVisible = true
        }

        holder.title.text = homeList[position].title
        holder.webView.visibility = if (isWebViewVisible) View.VISIBLE else View.GONE
        holder.webView.loadUrl(url)
    }

    override fun getItemCount(): Int {
        return homeList.size
    }

    inner class Holder(binding: ItemRecyclerViewListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        val title = binding.title
        var webView: WebView = binding.imageView
        val information = binding.information
        val context: Context = itemView.context

        init {
            title.setOnClickListener(this)
            information.setOnClickListener(this)

            webView.settings.javaScriptEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.domStorageEnabled = true

            webView.webViewClient = WebViewClient()
        }
        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return

            when(view.id){
                /*R.id.playButton -> {
                    val currentItem = homeList[position]
                    val url = currentItem.getVideoUrl()
                    if (url.isNotEmpty()){
                        isWebViewVisible = true
                        notifyItemChanged(position)

                        webView.loadUrl(url)
                    }
                    Log.d("test", "실행 완료")
                }*/
                R.id.information -> {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.apply {
                        putExtra("thumbNailUrl", homeList[position].thumbnail)
                        putExtra("title", homeList[position].title)
                        putExtra("id",homeList[position].getVideoUrl())

                    }
                    Log.d("test", "실행 완료")
                    context.startActivity(intent)
                }
            }
        }
    }

    fun updateData(newData: List<HomeItemModel>) {
        homeList = newData.toMutableList()
        notifyDataSetChanged()
    }

}