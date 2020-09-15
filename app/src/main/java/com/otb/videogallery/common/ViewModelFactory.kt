package com.otb.videogallery.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.otb.videogallery.scene.videos.VideosRepository
import com.otb.videogallery.scene.videos.VideosViewModel

/**
 * Created by Mohit Rajput on 14/9/20.
 */
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideosViewModel::class.java)) {
            return VideosViewModel(VideosRepository()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}