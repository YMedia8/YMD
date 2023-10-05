package com.example.ymd.retrofit

object Constants {

    // youtube  API의 기본 URL입니다.
    const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    // youtube API를 사용하기 위한 인증 헤더입니다.
    const val AUTH_HEADER = "AIzaSyAOLj6JLQf-VS3NDoF6CwgUwO5RTi-S8bg"

//    "AIzaSyBaTftuay-7bov4muIG4oeVRtrHJ4E15FU" 소진
//    "AIzaSyAOLj6JLQf-VS3NDoF6CwgUwO5RTi-S8bg" 선호(사용중)

    // 앱의 Shared Preferences 파일 이름입니다.
    const val PREFS_NAME = "com.example.ymd.prefs"

    // 마지막 검색어를 저장하기 위한 키 값입니다.
    const val PREF_KEY = "VIDEO_SEARCH_PREF"

}