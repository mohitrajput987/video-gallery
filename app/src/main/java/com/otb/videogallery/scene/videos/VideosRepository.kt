package com.otb.videogallery.scene.videos

import android.os.Environment
import com.otb.videogallery.data.model.Video
import io.reactivex.Single
import java.io.File
import java.util.*

/**
 * Created by Mohit Rajput on 14/9/20.
 */
class VideosRepository : VideosContract.Repository {
    override fun getDeviceVideos(): Single<List<Video>> {
        val listVideos = mutableListOf<Video>()
        findVideos(Environment.getExternalStorageDirectory(), listVideos)
        return Single.just(listVideos)
    }

    private fun findVideos(dir: File, list: MutableList<Video>) {
        for (file in dir.listFiles()) {
            if (file.isDirectory)
                findVideos(file, list)
            else if (file.absolutePath.contains(".mp4"))
                list.add(Video(UUID.randomUUID().toString(), file.name, file.absolutePath))
        }
    }
}