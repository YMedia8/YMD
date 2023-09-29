package com.example.ymd.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ymd.databinding.FragmentHomeBinding
import com.example.ymd.home.homeAdapter.HomeAdapter
import com.example.ymd.home.homeViewModel.HomeViewModel
import com.example.ymd.retrofit.YMDClient.api
import com.example.ymd.retrofit.youtubeData.VideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var homeList = mutableListOf<HomeItemModel>()
    private var homeAdapter = HomeAdapter(homeList)
    private lateinit var viewModel: HomeViewModel
    private var items = mutableListOf<HomeItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewList.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }

        viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]

        viewModel.homeList.observe(viewLifecycleOwner, Observer { newVideo ->
            homeAdapter.updateData(newVideo)
        })
        mostVideoData()
    }

    private fun mostVideoData() {
        api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            apiKey = "AIzaSyBaTftuay-7bov4muIG4oeVRtrHJ4E15FU"
        ).enqueue(object : Callback<VideoData?>{
            override fun onResponse(
                call: Call<VideoData?>,
                response: Response<VideoData?>
            ){
                if (response.isSuccessful){
                    response.body()?.items?.forEach {
                        val thumbnail = it.snippet.thumbnails.high.url
                        val title = it.snippet.title
                        val descriptor = it.snippet.description

                        items.add(HomeItemModel(thumbnail,title, descriptor))
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                viewModel.viewList(items)
            }

            override fun onFailure(call: Call<VideoData?>, t: Throwable){
                Log.e("#api", "실패: ${t.message}")
            }
        })
    }
}