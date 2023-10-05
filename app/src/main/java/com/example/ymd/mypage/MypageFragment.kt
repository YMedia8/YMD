package com.example.ymd.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ymd.MainActivity
import com.example.ymd.R
import com.example.ymd.Utils
import com.example.ymd.databinding.FragmentMypageBinding
import com.example.ymd.hot.HotItemModel

class MypageFragment : Fragment() {

    private lateinit var mypageContext : Context
    private var binding : FragmentMypageBinding? =null
    private lateinit var adapter : MypageAdapter

    private var likedVideo : List<HotItemModel> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mypageContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MypageAdapter(mypageContext).apply {
            video = likedVideo.toMutableList()
        }
        binding = FragmentMypageBinding.inflate(inflater, container, false).apply {
            rvBookmark.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rvBookmark.adapter = adapter
        }

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        likedVideo=Utils.getPrefBookmarkItems(mypageContext)
        adapter.video = likedVideo.toMutableList()
        adapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

}