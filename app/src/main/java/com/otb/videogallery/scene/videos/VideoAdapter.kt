package com.otb.videogallery.scene.videos

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.otb.videogallery.BR
import com.otb.videogallery.data.model.Video
import com.otb.videogallery.databinding.LayoutItemVideoBinding


/**
 * Created by Mohit Rajput on 14/9/20.
 */
@Suppress("DEPRECATION")
class VideoAdapter(private val videos: List<Video>, private val videoBookmarkListener: VideoBookmarkListener) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(private val binding: LayoutItemVideoBinding, private val videoAdapter: VideoAdapter) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.video = video
            binding.setVariable(BR.callback, videoAdapter)
            setupPlayer(binding.videoView, video)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val itemBinding: LayoutItemVideoBinding = LayoutItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(itemBinding, this)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
    }

    fun updateBookmark(video: Video) {
        video.isBookmarked = video.isBookmarked.not()
        videoBookmarkListener.onBookmarkBtnClicked(video.id)
        notifyDataSetChanged()
    }

    private fun setupPlayer(videoView: SimpleExoPlayerView, video: Video): SimpleExoPlayer {
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val player = ExoPlayerFactory.newSimpleInstance(videoView.context, trackSelector)
        videoView.player = player

        val playerInfo: String = Util.getUserAgent(videoView.context, "video-gallery")
        val dataSourceFactory = DefaultDataSourceFactory(videoView.context, playerInfo)
        val mediaSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(video.url))
        val loopingSource = LoopingMediaSource(mediaSource)
        video.player = player
        player.prepare(loopingSource)
        return player
    }
}

interface VideoBookmarkListener {
    fun onBookmarkBtnClicked(videoId: String)
}