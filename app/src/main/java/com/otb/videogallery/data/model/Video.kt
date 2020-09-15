package com.otb.videogallery.data.model

import com.google.android.exoplayer2.SimpleExoPlayer

/**
 * Created by Mohit Rajput on 14/9/20.
 */
data class Video(
    val id: String,
    val title: String,
    val url: String,
    var player : SimpleExoPlayer? = null,
    var isBookmarked : Boolean  = false
)