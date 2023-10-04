package com.example.ymd

import android.app.Activity
import android.content.Context
import com.example.ymd.hot.HotItemModel
import com.google.gson.GsonBuilder

object Utils {
    /**
     * Shared Preferences에 아이템을 추가하는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @param item 추가하려는 아이템
     */
    fun addPrefItem(context: Context, item: HotItemModel) {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = GsonBuilder().create()
        editor.putString(item.thumbnail, gson.toJson(item))
        editor.apply()
    }

    /**
     * Shared Preferences에서 특정 URL을 키로 하는 아이템을 제거하는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @param url 제거하려는 아이템의 URL
     */
    fun deletePrefItem(context: Context, url: String) {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(url)
        editor.apply()
    }

    /**
     * Shared Preferences에서 모든 북마크 아이템을 가져오는 함수.
     *
     * @param context 앱의 현재 컨텍스트
     * @return 북마크된 아이템의 ArrayList
     */
    fun getPrefBookmarkItems(context: Context): ArrayList<HotItemModel> {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val allEntries: Map<String, *> = prefs.all
        val bookmarkItems = ArrayList<HotItemModel>()
        val gson = GsonBuilder().create()
        for ((key, value) in allEntries) {
            val item = gson.fromJson(value as String, HotItemModel::class.java)
            bookmarkItems.add(item)
        }
        return bookmarkItems
    }
}