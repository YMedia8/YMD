package com.example.ymd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ymd.Home.HomeFragment
import com.example.ymd.Hot.HotFragment
import com.example.ymd.Mypage.MypageFragment
import com.example.ymd.databinding.ActivityMainBinding
import com.example.ymd.Search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        binding.mainNotification.setOnClickListener {

        }
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
                0 -> tab.setIcon(R.drawable.home).text = "com/example/ymd/Home"
                1 -> tab.setIcon(R.drawable.hot).text = "com/example/ymd/Hot"
                2 -> tab.setIcon(R.drawable.mymedia).text = "com/example/ymd/Mypage"
                3 -> tab.setIcon(R.drawable.search).text = "Search"
            }
        }.attach()
    }
}