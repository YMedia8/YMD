package com.example.ymd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ymd.databinding.ActivityMainBinding
import com.example.ymd.databinding.FragmentHomeBinding
import com.example.ymd.databinding.FragmentHotBinding
import com.example.ymd.databinding.FragmentMypageBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var tab1: FragmentHomeBinding
    lateinit var tab2: FragmentHotBinding
    lateinit var tab3: FragmentMypageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(HomeFragment())
        viewPager2Adatper.addFragment(HotFragment())
        viewPager2Adatper.addFragment(MypageFragment())

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
                0 -> tab.text = "Home"
                1 -> tab.text = "Hot"
                2 -> tab.text = "Mypage"
            }
        }.attach()
    }
}