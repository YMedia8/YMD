package com.example.ymd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ymd.hot.HotFragment
import com.example.ymd.home.HomeFragment
import com.example.ymd.mypage.MypageFragment
import com.example.ymd.databinding.ActivityMainBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.hot.HotAdapter
import com.example.ymd.hot.HotItemModel
import com.example.ymd.retrofit.Constants
import com.example.ymd.search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var MainContext :Context
    lateinit var binding: ActivityMainBinding
//    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>

    var likeVideo = mutableListOf<HotItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        binding.mainNotification.setOnClickListener {

        }
//
//        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            if(it.resultCode == RESULT_OK){
//                val itemIndex = it.data?.getIntExtra("itemIndex", 0) as Int
//                val like = it.data?.getBooleanExtra("isLike", false) as Boolean
//
//                if(like){
//                    likeVideo[itemIndex].favorites = true
//                    Log.d("mainActivity", "sj like")
//                }
//                else {
//                    if(likeVideo[itemIndex].favorites){
//                        likeVideo[itemIndex].favorites = false
//                    }
//                }
//            }
//            val intent = Intent(this, DetailActivity::class.java)
//            activityResultLauncher.launch(intent)
//        }
    }

    private fun initViewPager() {
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(HomeFragment())
        viewPager2Adatper.addFragment(HotFragment())
        viewPager2Adatper.addFragment(MypageFragment())
        viewPager2Adatper.addFragment(SearchFragment())

        binding.mainViewpager.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        TabLayoutMediator(binding.tableLayout, binding.mainViewpager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.home).text = "Home"
                1 -> tab.setIcon(R.drawable.hot).text = "Hot"
                2 -> tab.setIcon(R.drawable.mymedia).text = "Mypage"
                3 -> tab.setIcon(R.drawable.search).text = "Search"
            }
        }.attach()
    }
    fun addLikedVideo(items:HotItemModel){
        if(!likeVideo.contains(items)){
            likeVideo.add(items)
        }
    }
    fun removeLikedVideo(items: HotItemModel){
        likeVideo.remove(items)
    }
}