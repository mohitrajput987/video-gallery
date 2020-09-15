package com.otb.videogallery.scene.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otb.videogallery.data.model.DataResult
import com.otb.videogallery.data.model.Error
import com.otb.videogallery.data.model.Success
import com.otb.videogallery.data.model.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mohit Rajput on 14/9/20.
 */
class VideosViewModel(private val repository: VideosContract.Repository) : ViewModel(),
    VideosContract.ViewModel {
    private val videos = MutableLiveData<DataResult<List<Video>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchVideos()
    }

    private fun fetchVideos() {
        compositeDisposable.add(
            repository.getDeviceVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    videos.postValue(Success(it))
                }, {
                    videos.postValue(Error("Something went wrong"))
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    override fun getVideos(): LiveData<DataResult<List<Video>>> = videos

    override fun updateBookmark(videoId: String) {
        repository.updateBookmark(videoId)
    }
}