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
import com.example.ymd.R
import com.example.ymd.databinding.HotItemBinding
import com.example.ymd.datail.DetailActivity

class HotAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HotAdapter.ItemViewHolder>() {

    var items = ArrayList<HotItemModel>()

    private var isWebViewVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(mContext)
            .load(currentItem.thumbnail)
            .into(holder.hotthumbnail)

        holder.tv_title.text = currentItem.title
        holder.hotsubtitle.text = currentItem.description
        holder.webView.visibility = if (isWebViewVisible) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: HotItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var hotthumbnail: ImageView = binding.thumbnail
        var tv_title: TextView = binding.title
        var hotsubtitle: TextView = binding.subTitle
        var cl_thumb_item: ConstraintLayout = binding.clThumbItem
        var plusbt: ImageView = binding.plusbt
        var playbt: ImageView = binding.playbt
        var inforbt: ImageView = binding.inforbt
        var webView: WebView = binding.hotweb

        init {
            hotthumbnail.setOnClickListener(this)
            cl_thumb_item.setOnClickListener(this)
            plusbt.setOnClickListener(this)
            playbt.setOnClickListener(this)
            inforbt.setOnClickListener(this)

            // WebView 설정
            webView.settings.javaScriptEnabled = true // JavaScript 활성화 (영상 재생을 위해 필요)
            webView.settings.loadsImagesAutomatically = true // 이미지 자동로드 활성화
            webView.settings.domStorageEnabled = true // DOM 스토리지 활성화



            // WebViewClient 설정
            webView.webViewClient = WebViewClient()
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return

            when (view.id) {
                R.id.thumbnail -> {
                    // 섬네일 클릭 시 해당 아이템의 URL을 가져와서 웹 뷰로 열도록 설정
                    val currentItem = items[position]
                    val url = currentItem.getVideoUrl() // YouTube 비디오 URL을 가져옴
                    if (url.isNotEmpty()) {
                        // URL이 비어 있지 않으면 웹뷰를 보이도록 설정
                        isWebViewVisible = true
                        notifyItemChanged(position)

                        // WebView에 URL 로드
                        webView.loadUrl(url)
                    }
                }
//                R.id.plusbt -> {
//                    val intent = Intent(mContext, MypageFragment::class.java)
//                    mContext.startActivity(intent)
//                }
                R.id.playbt -> {
                    // 섬네일 클릭 시 해당 아이템의 URL을 가져와서 웹 뷰로 열도록 설정
                    val currentItem = items[position]
                    val url = currentItem.getVideoUrl() // YouTube 비디오 URL을 가져옴
                    if (url.isNotEmpty()) {
                        // URL이 비어 있지 않으면 웹뷰를 보이도록 설정
                        isWebViewVisible = true
                        notifyItemChanged(position)

                        // WebView에 URL 로드
                        webView.loadUrl(url)
                    }
                }
                R.id.inforbt -> {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
        }
    }
}
