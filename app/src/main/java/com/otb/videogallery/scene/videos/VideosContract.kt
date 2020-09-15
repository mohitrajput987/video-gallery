package com.otb.videogallery.scene.videos

import androidx.lifecycle.LiveData
import com.otb.videogallery.data.model.DataResult
import com.otb.videogallery.data.model.Video
import io.reactivex.Single

/**
 * Created by Mohit Rajput on 14/9/20.
 */
class VideosContract {
    interface ViewModel {
        fun getVideos(): LiveData<DataResult<List<Video>>>
    }

    interface Repository {
        fun getDeviceVideos(): Single<List<Video>>
    }
}