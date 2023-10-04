package com.example.ymd.home.homeAdapter

import android.content.Context
import android.content.Intent
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
import com.example.ymd.home.homeItemModel.CategoryVideoItemModel

class CategoryVideoAdapter(private var categoryList: MutableList<CategoryVideoItemModel>) : RecyclerView.Adapter<CategoryVideoAdapter.Holder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    private var itemClick: ItemClick? = null

    private var isWebViewVisible = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerViewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
//        val videoUrl = categoryList[position].thumbnail
//        Glide.with(holder.video.context)
//            .load(videoUrl)
//            .into(holder.video)
        val url = categoryList[position].getVideoUrl()
        if (url.isNotEmpty()){
            isWebViewVisible = true
        }
        holder.title.text = categoryList[position].title
        holder.webView.visibility = if (isWebViewVisible) View.VISIBLE else View.GONE
        holder.webView.loadUrl(url)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class Holder(binding: ItemRecyclerViewListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        val title = binding.title
        var webView: WebView = binding.imageView
        var playbt: ImageView = binding.playButton
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
                R.id.playbt -> {
                    val currentItem = categoryList[position]
                    val url = currentItem.getVideoUrl()
                    if (url.isNotEmpty()){
                        isWebViewVisible = true
                        notifyItemChanged(position)

                        webView.loadUrl(url)
                    }
                }
                R.id.information -> {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.apply {
                        putExtra("thumbNailUrl", categoryList[position].thumbnail)
                        putExtra("title", categoryList[position].title)
                        putExtra("id",categoryList[position].getVideoUrl())

                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    fun updateData(newData: List<CategoryVideoItemModel>){
        categoryList = newData.toMutableList()
        notifyDataSetChanged()
    }
}