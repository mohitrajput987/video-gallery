package com.otb.videogallery.scene.videos

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
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
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.otb.videogallery.R
import com.otb.videogallery.data.model.Video
import kotlinx.android.synthetic.main.layout_item_video.view.*


/**
 * Created by Mohit Rajput on 14/9/20.
 */
class VideoAdapter(private val videos: List<Video>, private val videoBookmarkListener: VideoBookmarkListener) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_video, parent, false))
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        val itemView = holder.itemView

        setupPlayer(itemView, video)
        itemView.tvVideoDescription.text = video.title
        itemView.ivBookmark.setImageResource(if (video.isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_border)

        itemView.ivBookmark.setOnClickListener {
            video.isBookmarked = video.isBookmarked.not()
            videoBookmarkListener.onBookmarkBtnClicked(video.id)
            notifyItemChanged(position)
        }
    }

    private fun setupPlayer(itemView: View, video: Video): SimpleExoPlayer {
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val player = ExoPlayerFactory.newSimpleInstance(itemView.context, trackSelector)
        itemView.videoView.player = player

        val playerInfo: String = Util.getUserAgent(itemView.context, "video-gallery")
        val dataSourceFactory = DefaultDataSourceFactory(itemView.context, playerInfo)
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