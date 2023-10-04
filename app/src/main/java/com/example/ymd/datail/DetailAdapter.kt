package com.example.ymd.hot

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.MainActivity
import com.example.ymd.R
import com.example.ymd.databinding.DetailItemBinding
import com.example.ymd.databinding.HotItemBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.datail.DetailModel
import com.example.ymd.mypage.MypageFragment

class DetailAdapter(private val mContext: Context) :
    RecyclerView.Adapter<DetailAdapter.ItemViewHolder>() {

    var items = ArrayList<DetailModel>()

    private var isWebViewVisible = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        val url = currentItem.getVideoUrl()
        if (url.isNotEmpty()) {
            isWebViewVisible = true
        }

        Glide.with(mContext)
            .load(currentItem.channelId)
            .into(holder.channelId)

        holder.webView.loadUrl(url)
        holder.webView.visibility = if (isWebViewVisible) View.VISIBLE else View.GONE
        holder.title.text = currentItem.title
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: DetailItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var webView: WebView = binding.detailItWb //동영상재생
        var channelId: ImageView = binding.detailItemProfile //채널 id
        var inforbt: ImageView = binding.detailItemInfo //detail정보
        var title: TextView = binding.detailItemTitle // 타이틀 제목
        var thumb_item: ConstraintLayout = binding.detailIt // 아이템

        init {
            thumb_item.setOnClickListener(this)
            inforbt.setOnClickListener(this)

            webView.settings.javaScriptEnabled = true // JavaScript 활성화 (영상 재생을 위해 필요)
            webView.settings.loadsImagesAutomatically = true // 이미지 자동로드 활성화
            webView.settings.domStorageEnabled = true // DOM 스토리지 활성화

            webView.webViewClient = WebViewClient()
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return

            when (view.id) {
                R.id.plusbt -> {
                    val intent = Intent(mContext, MypageFragment::class.java)
                    mContext.startActivity(intent)
                }
                R.id.playbt -> {
                    val currentItem = items[position]
                    val url = currentItem.getVideoUrl() // YouTube 비디오 URL을 가져옴
                    if (url.isNotEmpty()) {
                        isWebViewVisible = true
                        notifyItemChanged(position)

                        webView.loadUrl(url)
                    }
                }
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

