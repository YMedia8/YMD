package com.example.ymd.search

import android.content.Context
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
            Log.e("click","click")
            val query = binding.searchText.text.toString()
            if(query.isNotEmpty()){
                adapter.clearItem()
                searchResultListener(query)
            }else {
                Toast.makeText(sContext,"검색어를 입력하세요.",Toast.LENGTH_SHORT).show()
            }
            hideKb.hideSoftInputFromWindow(binding.searchText.windowToken,0)
        }
    }


    
    private fun searchResultListener(query:String) {
        api.search("KR",Constants.AUTH_HEADER,query)
            ?.enqueue(object :Callback<Search?>{
                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
                    if(response.isSuccessful){
                        response.body()!!.items.forEach {
                            val thumbnailUrl=it.snippet.thumbnails.default.url
                            val title=it.snippet.title
                            searchItems.add(SearchItemModel(title,thumbnailUrl))
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