package com.otb.videogallery.scene.videos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.otb.videogallery.R
import com.otb.videogallery.common.ViewModelFactory
import com.otb.videogallery.common.longToast
import com.otb.videogallery.data.model.Error
import com.otb.videogallery.data.model.Success
import com.otb.videogallery.data.model.Video
import kotlinx.android.synthetic.main.activity_videos.*


class VideosActivity : AppCompatActivity() {
    private lateinit var videosViewModel: VideosViewModel
    private val videos = mutableListOf<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)
        init()
        setupViewModel()
    }

    private fun init() {
        rvVideos.layoutManager = LinearLayoutManager(this)
        rvVideos.adapter = VideoAdapter(videos)
        PagerSnapHelper().attachToRecyclerView(rvVideos)
        rvVideos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisiblePosition = (rvVideos.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                videos.forEachIndexed { index, video ->
                    kotlin.run {
                        video.player?.playWhenReady = index == lastVisiblePosition
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        videosViewModel =
                ViewModelProviders.of(this, ViewModelFactory()).get(VideosViewModel::class.java)

        videosViewModel.getVideos().observe(this, Observer {
            when (it) {
                is Success -> {
                    displayVideos(it.data)
                }
                is Error -> {
                    displayError(it.errorMessage)
                }
            }
        })
    }

    private fun displayVideos(videos: List<Video>) {
        this.videos.clear()
        this.videos.addAll(videos)
        rvVideos.adapter?.notifyDataSetChanged()
    }

    private fun displayError(message: String) {
        longToast(message)
    }
}