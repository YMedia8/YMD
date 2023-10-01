package com.example.ymd.datail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ymd.R
import com.example.ymd.databinding.FragmentHotBinding
import com.example.ymd.hot.HotAdapter
import com.example.ymd.hot.HotItemModel
import com.example.ymd.retrofit.YMDClient
import com.example.ymd.retrofit.youtubeData.VideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivity
    private lateinit var gridManager: StaggeredGridLayoutManager
    private lateinit var mContext: Context
    private lateinit var adapter: HotAdapter
    private var resItems: ArrayList<HotItemModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail2)

        val btn_close = findViewById<ImageView>(R.id.detail_close) // 종료버튼 아이디가져오기




        btn_close.setOnClickListener {     // 종료 누르면 main화면으로 돌아가기

            finish()
        }
    }
//    private fun setupViews() {
//        // RecyclerView 설정
//        gridManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
//        binding.hotrecyclerViewList.layoutManager = gridManager
//
//        adapter = HotAdapter(mContext)
//        binding.hotrecyclerViewList.adapter = adapter
//        binding.hotrecyclerViewList.itemAnimator = null
//    }

//    private fun setupListeners() {
//        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        binding.hotsearch.setOnClickListener {
//            // 검색 버튼을 클릭할 때 최신 영상을 가져오는 함수 호출
//            fetchImageResults()
//        }
//    }

    private fun fetchImageResults() {
        YMDClient.api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            apiKey = "AIzaSyBaTftuay-7bov4muIG4oeVRtrHJ4E15FU"
        ).enqueue(object : Callback<VideoData?> {
            override fun onResponse(
                call: Call<VideoData?>,
                response: Response<VideoData?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.items?.forEach {
                        val descriptor = it.snippet.description
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url

                        resItems.add(HotItemModel(descriptor, title, thumbnail))
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

//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mContext = context
//    }
}