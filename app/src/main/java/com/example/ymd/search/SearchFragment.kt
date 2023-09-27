package com.example.ymd.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ymd.databinding.FragmentSearchBinding
import com.example.ymd.retrofit.Constants
import com.example.ymd.retrofit.search.Search
import com.example.ymd.retrofit.YMDClient.api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    private fun searchImageResults() {
        api.search("KR",Constants.AUTH_HEADER,"포켓몬")
            ?.enqueue(object :Callback<Search?>{
                override fun onResponse(call: Call<Search?>, response: Response<Search?>) {
                    if(response.isSuccessful){
                        response.body()!!.items.forEach {
                            val url=it.snippet.thumbnails.default.url
                            val title=it.snippet.title
                        }
                    }
                }

                override fun onFailure(call: Call<Search?>, t: Throwable) {
                    Log.e("fail","${t.message}")
                }
            })
    }

}