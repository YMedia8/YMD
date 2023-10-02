package com.example.ymd

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstance : Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.splash_image)

        var handler=Handler()
        handler.postDelayed({
            val intent= Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        },2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}