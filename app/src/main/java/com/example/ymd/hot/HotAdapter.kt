package com.example.ymd.hot

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ymd.MainActivity
import com.example.ymd.R
import com.example.ymd.databinding.HotItemBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.mypage.MypageFragment

class HotAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HotAdapter.ItemViewHolder>() {

    var items = ArrayList<HotItemModel>()

    private var isWebViewVisible = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        val url = currentItem.getVideoUrl()
        if (url.isNotEmpty()) {
            isWebViewVisible = true

        }

        holder.tv_title.text = currentItem.title
        holder.hotsubtitle.text = currentItem.description
        holder.webView.visibility = if (isWebViewVisible) View.VISIBLE else View.GONE
        holder.webView.loadUrl(url)
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: HotItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var tv_title: TextView = binding.title
        var hotsubtitle: TextView = binding.subTitle
        var cl_thumb_item: ConstraintLayout = binding.clThumbItem
        var inforbt: ImageView = binding.inforbt
        var webView: WebView = binding.hotweb

        init {
            cl_thumb_item.setOnClickListener(this)
            inforbt.setOnClickListener(this)

            webView.settings.javaScriptEnabled = true // JavaScript 활성화 (영상 재생을 위해 필요)
            webView.settings.loadsImagesAutomatically = true // 이미지 자동로드 활성화
            webView.settings.domStorageEnabled = true // DOM 스토리지 활성화



            webView.webViewClient = WebViewClient()
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return

            when (view.id) {
                R.id.inforbt -> {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    intent.apply {
                        putExtra("thumbNailUrl", items[position].thumbnail)
                        putExtra("title", items[position].title)
                        putExtra("id",items[position].getVideoUrl())

                    }
                    mContext.startActivity(intent)
                }
            }
        }
    }
}