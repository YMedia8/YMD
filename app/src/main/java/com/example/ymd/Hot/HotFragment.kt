package com.example.ymd.Hot

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ymd.databinding.FragmentHotBinding
import com.example.ymd.databinding.FragmentSearchBinding


class HotFragment : Fragment() {

    private lateinit var binding: FragmentHotBinding
    private lateinit var gridmanager: StaggeredGridLayoutManager
    private lateinit var mContext: Context
    private lateinit var adapter: HotAdapter

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


        return binding.root

    }

}