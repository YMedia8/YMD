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
import com.bumptech.glide.util.Util
import com.example.ymd.R
import com.example.ymd.Utils
import com.example.ymd.databinding.ActivityDetailBinding
import com.example.ymd.databinding.FragmentHotBinding
import com.example.ymd.hot.HotAdapter
import com.example.ymd.hot.HotItemModel
import com.example.ymd.retrofit.Constants
import com.example.ymd.retrofit.YMDClient
import com.example.ymd.retrofit.YMDClient.api
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

    private lateinit var gridManager: StaggeredGridLayoutManager
    private lateinit var adapter: DetailAdapter
    private var resItems: ArrayList<DetailModel> = ArrayList()

    private lateinit var img:String
    private lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this // Context 초기화
        val btn_close = findViewById<ImageView>(R.id.detail_close) // 종료버튼 아이디가져오기
        val detailImage = findViewById<ImageView>(R.id.detail_image) // 이미지 받아오기 위한 변수


        //받아올 사진 ID
        img = intent.getStringExtra("thumbNailUrl")!!
        Log.d("ddd", "id = $img")

        Glide.with(mContext)
            .load(img)
            .into(detailImage)

        // 받아올 타이틀 ID
        val detailTitle = findViewById<TextView>(R.id.detail_title)
        title = intent.getStringExtra("title")!!
        detailTitle.text = title

        // 좋아요 클릭시 Mypage 보관함에 저장
        binding.detailLike.setOnClickListener{
            if(!likeVideo){
                Snackbar.make(binding.constraintLayout2,"Mypage에 저장되었습니다.", Snackbar.LENGTH_SHORT).show()
                likeVideo = true
                Utils.addPrefItem(this, HotItemModel("", title, img,"",true ))
            }
            else likeVideo = false

        }


        shareAction() // 공유기능 버튼


        setupViews()
        fetchImageResults()


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
    //연관 검색어
    private fun setupViews() {
        gridManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.dtRv.layoutManager = gridManager

        adapter = DetailAdapter(mContext)
        binding.dtRv.adapter = adapter
        binding.dtRv.itemAnimator = null

    }
    //api 연결
    private fun fetchImageResults() {
        api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            Constants.AUTH_HEADER
        ).enqueue(object : Callback<VideoData?> {
            override fun onResponse(
                call: Call<VideoData?>,
                response: Response<VideoData?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.forEach {
                        val title = it.snippet.title
                        val id = it.id

                        resItems.add(DetailModel( title, id))
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                adapter.items = resItems
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<VideoData?>, t: Throwable) {
                Log.e("#api", "실패: ${t.message}")
            }
        })
    }
//    private fun chanelIDResults() {
//        YMDClient.api.channels(
//            part = "snippet",
//            chart = "mostPopular",
//            maxResults = 20,
//            regionCode = "KR",
//            apiKey = "AIzaSyBaTftuay-7bov4muIG4oeVRtrHJ4E15FU"
//        )
//    }

}