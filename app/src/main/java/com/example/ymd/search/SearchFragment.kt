package com.example.ymd.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ymd.databinding.FragmentSearchBinding
import com.example.ymd.retrofit.Constants
import com.example.ymd.retrofit.search.Search
import com.example.ymd.retrofit.YMDClient.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var sContext : Context
    private lateinit var adapter : SearchAdapter
    private lateinit var gridmanager : StaggeredGridLayoutManager

    private var searchItems = ArrayList<SearchItemModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sContext=context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        setupView()
        setupListener()
        return binding.root
    }

    private fun setupView() {
        gridmanager=StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.searchRv.layoutManager=gridmanager

        adapter=SearchAdapter(sContext)
        binding.searchRv.adapter=adapter
        binding.searchRv.itemAnimator=null
    }

    private fun setupListener() {
        val hideKb=requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.searchBtn.setOnClickListener {
            val query = binding.searchText.text.toString()
            if(query.isNotEmpty()){
                adapter.clearItem()
                searchResultListener(query)
            }else {
                Toast.makeText(sContext,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            hideKb.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        }

        //음악 카테고리 버튼
        binding.music.setOnClickListener {
            val query = binding.searchText.text.toString()
            if(query.isNotEmpty()){
                adapter.clearItem()
                searchResultListener(query,"10")
            }else {
                Toast.makeText(sContext,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            hideKb.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        }

        //스포츠 카테고리 버튼
        binding.sports.setOnClickListener {
            val query = binding.searchText.text.toString()
            if(query.isNotEmpty()){
                adapter.clearItem()
                searchResultListener(query,"17")
            }else {
                Toast.makeText(sContext,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            hideKb.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        }

        //게임 카테고리 버튼
        binding.game.setOnClickListener {
            val query = binding.searchText.text.toString()
            if(query.isNotEmpty()){
                adapter.clearItem()
                searchResultListener(query,"20")
            }else {
                Toast.makeText(sContext,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            hideKb.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        }
    }


    //전체
    private fun searchResultListener(query:String) {
        api.search("KR",Constants.AUTH_HEADER,query,null)
            ?.enqueue(object :Callback<Search?>{
                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
                    if(response.isSuccessful){
                        response.body()!!.items.forEach {
                            val thumbnailUrl=it.snippet.thumbnails.default.url
                            val title=it.snippet.title
                            val date=it.snippet.publishedAt.substring(0,10)
                            val video=it.id.videoId
                            searchItems.add(SearchItemModel(title,thumbnailUrl,date,video))
                        }
                    }
                    adapter.items=searchItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Search?>, t: Throwable) {
                    Log.e("fail","${t.message}")
                }
            })
    }

    //오버로딩, 카테고리별
    private fun searchResultListener(query:String,categoryId:String) {
        api.search("KR",Constants.AUTH_HEADER,query,categoryId)
            ?.enqueue(object :Callback<Search?>{
                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
                    if(response.isSuccessful){
                        Log.e("test","${response.body()}")
                        response.body()!!.items.forEach {
                            val thumbnailUrl=it.snippet.thumbnails.default.url
                            val title=it.snippet.title
                            val date=it.snippet.publishedAt.substring(0,10)
                            val video=it.id.videoId
                            searchItems.add(SearchItemModel(title,thumbnailUrl,date,video))
                        }
                    }
                    adapter.items=searchItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Search?>, t: Throwable) {
                    Log.e("fail","${t.message}")
                }
            })
    }

}