package com.example.ymd.datail

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.ymd.R
import com.example.ymd.databinding.ActivityDetailBinding
import com.example.ymd.hot.HotAdapter
import com.example.ymd.hot.HotItemModel
import com.example.ymd.retrofit.YMDClient
import com.example.ymd.retrofit.youtubeData.VideoData
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var binding: ActivityDetailBinding
    private var likeVideo = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this // Context 초기화
        val btn_close = findViewById<ImageView>(R.id.detail_close) // 종료버튼 아이디가져오기
        val detailImage = findViewById<ImageView>(R.id.detail_image) // 이미지 받아오기 위한 변수


        //받아올 사진 ID
        val imageData = intent.getStringExtra("thumbNailUrl")
        Log.d("ddd", "id = $imageData")

        Glide.with(mContext)
            .load(imageData)
            .into(detailImage)

        // 받아올 타이틀 ID
        val detailTitle = findViewById<TextView>(R.id.detail_title)
        val titleData = intent.getStringExtra("title")
        detailTitle.text = titleData

        // 좋아요 클릭시 Mypage 보관함에 저장
        binding.detailLike.setOnClickListener{
            if(!likeVideo){
                Snackbar.make(binding.constraintLayout2,"Mypage에 저장되었습니다.", Snackbar.LENGTH_SHORT).show()
                likeVideo = true
            }
            else likeVideo = false
        }


        shareAction() // 공유기능 버튼


        btn_close.setOnClickListener {     // 종료 누르면 main화면으로 돌아가기

            finish()
        }
    }

    //공유 기능
    private fun shareAction() {

        val share = findViewById<Button>(R.id.detail_share)
        share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    intent.getStringExtra("id")
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))

        }
    }
}