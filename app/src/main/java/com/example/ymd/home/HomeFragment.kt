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
import com.example.ymd.home.homeAdapter.CategoryChannelAdapter
import com.example.ymd.home.homeAdapter.HomeAdapter
import com.example.ymd.home.homeAdapter.CategoryVideoAdapter
import com.example.ymd.home.homeItemModel.CategoryChannelItemModel
import com.example.ymd.home.homeItemModel.CategoryItemModel
import com.example.ymd.home.homeItemModel.CategoryVideoItemModel
import com.example.ymd.home.homeItemModel.HomeItemModel
import com.example.ymd.home.homeViewModel.CategoryChannelViewModel
import com.example.ymd.home.homeViewModel.CategoryVideoViewModel
import com.example.ymd.home.homeViewModel.CategoryViewModel
import com.example.ymd.home.homeViewModel.HomeViewModel
import com.example.ymd.retrofit.Constants
import com.example.ymd.retrofit.YMDClient.api
import com.example.ymd.retrofit.categories.Categories
import com.example.ymd.retrofit.channel.Channel
import com.example.ymd.retrofit.youtubeData.VideoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private var homeList = mutableListOf<HomeItemModel>()
    private var categoryVideoList = mutableListOf<CategoryVideoItemModel>()
    private var categoryChannelList = mutableListOf<CategoryChannelItemModel>()

    private var homeAdapter = HomeAdapter(homeList)
    private var categoryVideoAdapter = CategoryVideoAdapter(categoryVideoList)
    private var categoryChannelAdapter = CategoryChannelAdapter(categoryChannelList)

    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryVideoViewModel: CategoryVideoViewModel
    private lateinit var categoryChannelViewModel: CategoryChannelViewModel

    private var items = mutableListOf<HomeItemModel>()
    private var categoryItems = mutableListOf<CategoryItemModel>()
    private var categoryVideoItems = mutableListOf<CategoryVideoItemModel>()
    private var categoryChannelItems = mutableListOf<CategoryChannelItemModel>()

    private var categoryId = HashMap<String, String>()
    private var channelList = mutableListOf<String>()

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

        binding.recyclerViewCategory.apply {
            adapter = categoryVideoAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.recyclerViewChannel.apply {
            adapter = categoryChannelAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
        categoryViewModel = ViewModelProvider(this@HomeFragment)[CategoryViewModel::class.java]
        categoryVideoViewModel = ViewModelProvider(this@HomeFragment)[CategoryVideoViewModel::class.java]
        categoryChannelViewModel = ViewModelProvider(this@HomeFragment)[CategoryChannelViewModel::class.java]

        viewModel.homeList.observe(viewLifecycleOwner, Observer { newVideo ->
            homeAdapter.updateData(newVideo)
        })
        mostVideoData()
        categoryViewModel.categoryList.observe(viewLifecycleOwner, Observer { newCategory ->
            val categoryTitleList = newCategory.map { it.title }
            binding.spinnerViewSide.setItems(categoryTitleList)
        })

        categoryData()

        binding.spinnerViewSide.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            categoryVideoViewModel.clearCategoryVideo()
            categoryChannelViewModel.clearCategoryChannel()
            categoryVideoItems.clear()
            categoryChannelItems.clear()

            categoryId[text]?.let { categoryVideo(it) }
        }
        categoryVideoViewModel.categoryVideoList.observe(viewLifecycleOwner, Observer { newCategoryVideo ->
            if (newCategoryVideo != null) {
                categoryVideoAdapter.updateData(newCategoryVideo)
            }
        })
        categoryChannelViewModel.categoryChannelList.observe(viewLifecycleOwner, Observer { newChannel ->
            if (newChannel != null) {
                categoryChannelAdapter.updateData(newChannel)
            }
        })
    }

    private fun mostVideoData() {
        api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            apiKey = Constants.AUTH_HEADER
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
                        val id = it.id

                        items.add(HomeItemModel(thumbnail,title, descriptor, id, false))
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                viewModel.viewList(items)
            }

            override fun onFailure(call: Call<VideoData?>, t: Throwable){
                Log.e("#api1", "실패: ${t.message}")
            }
        })
    }

    private fun categoryData(){
        api.category(
            part = "snippet",
            key = Constants.AUTH_HEADER,
            regionCode = "KR"
        ).enqueue(object : Callback<Categories?>{
            override fun onResponse(call: Call<Categories?>, response: Response<Categories?>) {
                if (response.isSuccessful){
                    response.body()?.items?.forEach {
                        val title = it.snippet.title
                        val filter = it.snippet.assignable
                        val channel = it.snippet.channelId
                        val category = it.id

                        if (filter){
                            categoryItems.add(CategoryItemModel(title, filter, channel))
                        }
                        categoryId[title] = category
                    }
                } else {
                    Log.e("api2", "Error: ${response.errorBody()}")
                }
                categoryViewModel.category(categoryItems)
                Log.d("categoryData", "${categoryItems.size}")
            }

            override fun onFailure(call: Call<Categories?>, t: Throwable) {
                Log.e("#api", "실패: ${t.message}")
            }

        })
    }

    private fun categoryVideo(category: String){
        api.video(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 20,
            regionCode = "KR",
            apiKey = Constants.AUTH_HEADER,
            category
        ).enqueue(object : Callback<VideoData?>{
            override fun onResponse(call: Call<VideoData?>, response: Response<VideoData?>) {
                if(response.isSuccessful){
                    response.body()?.items?.forEach {
                        val title = it.snippet.title
                        val video = it.snippet.thumbnails.high.url
                        val descriptor = it.snippet.description
                        val channelId = it.snippet.channelId
                        val id = it.id

                        categoryVideoItems.add(CategoryVideoItemModel(video,title,descriptor, id))
                        channelList.add(channelId)
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                categoryVideoViewModel.categoryVideo(categoryVideoItems)
                categoryChannel()
            }

            override fun onFailure(call: Call<VideoData?>, t: Throwable) {
                Log.e("#api3", "실패: ${t.message}")
            }

        })
    }

    private fun categoryChannel(){
        api.channels(
            "snippet",
            channelList,
            Constants.AUTH_HEADER
        ).enqueue(object : Callback<Channel?>{
            override fun onResponse(call: Call<Channel?>, response: Response<Channel?>) {
                if(response.isSuccessful){
                    response.body()?.items?.forEach {
                        val title = it.snippet.title
                        val video = it.snippet.thumbnails.high.url

                        categoryChannelItems.add(CategoryChannelItemModel(video,title))
                    }
                } else {
                    Log.e("api", "Error: ${response.errorBody()}")
                }
                categoryChannelViewModel.categoryChannel(categoryChannelItems)
            }

            override fun onFailure(call: Call<Channel?>, t: Throwable) {
                Log.e("#api3", "실패: ${t.message}")
            }

        })
    }
}