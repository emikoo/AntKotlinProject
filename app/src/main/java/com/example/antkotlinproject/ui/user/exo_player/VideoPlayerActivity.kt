package com.example.antkotlinproject.ui.user.exo_player

import android.content.pm.ActivityInfo
import android.net.Uri
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.ActivityVideoPlayerBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.user.detail_course.DetailCourseActivity.Companion.VIDEO
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class VideoPlayerActivity : BaseActivity<DefaultViewModel, ActivityVideoPlayerBinding>(
    DefaultViewModel::class
) {
    private var btFullScreen: ImageView? = null
    private var url: String? = null
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var flag: Boolean? = null

    override fun getViewBinding() = ActivityVideoPlayerBinding.inflate(layoutInflater)

    override fun setupViews() {

        val item = intent.getStringExtra(VIDEO)

        btFullScreen = binding.player.findViewById(R.id.bt_fullscreen)
        binding.player
        binding.progressBar

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
            , WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        url = item
        val uri = Uri.parse(url)

        val loadControl = DefaultLoadControl()
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(
            AdaptiveTrackSelection.Factory(bandwidthMeter)
        )

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl)

        val factory = DefaultHttpDataSourceFactory("exoplayer_video")
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ExtractorMediaSource(uri, factory, extractorsFactory, null, null)

        binding.player.player = simpleExoPlayer
        binding.player.keepScreenOn = true

        simpleExoPlayer?.prepare(mediaSource)
        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.addListener(object : Player.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
            override fun onSeekProcessed() {}
            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {
            }

            override fun onPlayerError(error: ExoPlaybackException?) {}
            override fun onLoadingChanged(isLoading: Boolean) {}
            override fun onPositionDiscontinuity(reason: Int) {}
            override fun onRepeatModeChanged(repeatMode: Int) {}
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) binding.progressBar.visibility =
                    View.VISIBLE
                else if (playbackState == Player.STATE_READY) binding.progressBar.visibility =
                    View.GONE
            }
        })

        btFullScreen?.setOnClickListener {
            if (flag == true) {
                btFullScreen?.setImageDrawable(getDrawable(R.drawable.ic_fullscreen))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            } else {
                btFullScreen?.setImageDrawable(getDrawable(R.drawable.ic_fullscreen_exit))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer?.playWhenReady = false
        simpleExoPlayer?.playbackState
    }

    override fun onRestart() {
        super.onRestart()

        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.playbackState
    }

    override fun subscribeToLiveData() {}
}