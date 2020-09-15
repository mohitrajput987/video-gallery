package com.otb.videogallery.scene.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.otb.videogallery.R
import com.otb.videogallery.scene.videos.VideosActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToVideos()
    }

    private fun goToVideos() {
        startActivity(Intent(this, VideosActivity::class.java))
        finish()
    }
}