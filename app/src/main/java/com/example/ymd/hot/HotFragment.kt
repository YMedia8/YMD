package com.example.ymd.hot

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ymd.databinding.FragmentHotBinding
import com.example.ymd.retrofit.Constants
import com.example.ymd.retrofit.YMDClient.api
import com.example.ymd.retrofit.youtubeData.VideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotFragment : Fragment() {

    private lateinit var binding: FragmentHotBinding
    private lateinit var gridManager: StaggeredGridLayoutManager
    private lateinit var mContext: Context
    private lateinit var adapter: HotAdapter
    private var resItems: ArrayList<HotItemModel> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotBinding.inflate(inflater, container, false)

        setupViews()
        fetchImageResults()

        return binding.root
    }

    private fun setupViews() {
        gridManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.hotrecyclerViewList.layoutManager = gridManager

        adapter = HotAdapter(mContext)
        binding.hotrecyclerViewList.adapter = adapter
        binding.hotrecyclerViewList.itemAnimator = null
    }

    private fun fetchImageResults() {
        api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            apiKey = Constants.AUTH_HEADER
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
                        val id = it.id

                        resItems.add(HotItemModel(descriptor, title, thumbnail, id))
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
}
