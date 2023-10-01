package com.example.ymd.hot

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ymd.R
import com.example.ymd.databinding.HotItemBinding
import com.example.ymd.datail.DetailActivity
import com.example.ymd.mypage.MypageFragment

class HotAdapter(private val mContext: Context) :
    RecyclerView.Adapter<HotAdapter.ItemViewHolder>() {

    var items = ArrayList<HotItemModel>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = HotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(mContext)
            .load(currentItem.thumbnail)
            .into(holder.hotthumbnail)

        holder.tv_title.text = currentItem.title
        holder.hotsubtitle.text = currentItem.id
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: HotItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var hotthumbnail: ImageView = binding.thumbnail
        var tv_title: TextView = binding.title
        var hotsubtitle: TextView = binding.subTitle
        var cl_thumb_item: ConstraintLayout = binding.clThumbItem
        var plusbt: ImageView = binding.plusbt
        var playbt: ImageView = binding.playbt
        var inforbt: ImageView = binding.inforbt

        init {
            hotthumbnail.setOnClickListener(this)
            cl_thumb_item.setOnClickListener(this)
            plusbt.setOnClickListener(this)
            playbt.setOnClickListener(this)
            inforbt.setOnClickListener(this)

        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return

            when (view.id) {
                R.id.thumbnail -> {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    mContext.startActivity(intent)
                }
//                R.id.plusbt -> {
//                    val intent = Intent(mContext, MypageFragment::class.java)
//                    mContext.startActivity(intent)
//                }
//                R.id.playbt -> {
//                    val intent = Intent(mContext, MypageFragment::class.java)
//                    mContext.startActivity(intent)
//                }
                R.id.inforbt -> {
                    val intent = Intent(mContext, MypageFragment::class.java)
                    mContext.startActivity(intent)
                }
            }
        }
    }

}