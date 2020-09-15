package com.otb.videogallery.scene.videos

import android.os.Environment
import com.otb.videogallery.data.model.Video
import com.youtility.utils.SharedPreferenceHelper
import io.reactivex.Single
import java.io.File

/**
 * Created by Mohit Rajput on 14/9/20.
 */
@Suppress("DEPRECATION")
class VideosRepository : VideosContract.Repository {
    companion object {
        private const val PREF_KEY_BOOKMARK = "bookmarked-videos"
    }

    private val listVideos = mutableListOf<Video>()
    override fun getDeviceVideos(): Single<List<Video>> {
        findVideos(Environment.getExternalStorageDirectory(), listVideos)
        return Single.just(listVideos)
    }

    override fun updateBookmark(videoId: String) {
        val bookmarkedVideos = SharedPreferenceHelper.getListString(PREF_KEY_BOOKMARK)
        if (bookmarkedVideos.contains(videoId))
            bookmarkedVideos.remove(videoId)
        else
            bookmarkedVideos.add(videoId)
        SharedPreferenceHelper.saveListString(PREF_KEY_BOOKMARK, bookmarkedVideos)
    }

    private fun findVideos(dir: File, list: MutableList<Video>) {
        val bookmarkedVideos = SharedPreferenceHelper.getListString(PREF_KEY_BOOKMARK)

        for (file in dir.listFiles()) {
            if (file.isDirectory)
                findVideos(file, list)
            else if (file.absolutePath.contains(".mp4")) {
                val fileId = getFileId(file)
                list.add(Video(getFileId(file), file.name, file.absolutePath, isBookmarked = bookmarkedVideos.contains(fileId))
                )
            }
        }
    }

    private fun getFileId(file: File): String {
        return file.nameWithoutExtension.replace(" ", "")
    }
}