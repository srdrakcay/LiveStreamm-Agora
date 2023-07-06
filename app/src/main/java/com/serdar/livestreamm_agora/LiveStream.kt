package com.serdar.livestreamm_agora

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.serdar.livestreamm_agora.databinding.ActivityLiveStreamBinding
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration

class LiveStream : AppCompatActivity() {
    private var isBroadcaster = false
    private var mRtcEngine: RtcEngine? = null
    private lateinit var binding: ActivityLiveStreamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clientRole()
        initAgoraEngineAndJoinChannel()
    }

    private fun clientRole() {
        val intent = intent
        val value = intent.getStringExtra("role")

        when (value) {
            "Broadcaster" -> {
                isBroadcaster = true
            }

            "Audience" -> {
                isBroadcaster = false
            }
        }
    }

    private fun initAgoraEngineAndJoinChannel() {
        try {

            mRtcEngine =
                RtcEngine.create(baseContext, "65570d88c9374327838ee8a183ebd383", mRtcEventHandler)

            val videoConfig = VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_1280x720,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_30,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE,
            )
            mRtcEngine?.setVideoEncoderConfiguration(videoConfig)

        } catch (e: Exception) {
            throw RuntimeException("Check the error.")
        }
        setAgoraUser()
    }
    private fun setAgoraUser(){

        if (isBroadcaster) {
            mRtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
            mRtcEngine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
            mRtcEngine?.enableVideo()
            setupLocalVideo()
            val container = binding.streamVideo
            val surfaceView = RtcEngine.CreateRendererView(baseContext)
            container.addView(surfaceView)
            mRtcEngine?.setupLocalVideo(
                VideoCanvas(
                    surfaceView,
                    VideoCanvas.RENDER_MODE_ADAPTIVE,
                    0
                )
            )
            mRtcEngine?.joinChannel(
                "65570d88c9374327838ee8a183ebd383", "srdrAkcay", null,
                0
            )
        } else {
            mRtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
            mRtcEngine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
            mRtcEngine?.enableVideo()
            setupLocalVideo()
            val container = binding.streamVideo
            val surfaceView = RtcEngine.CreateRendererView(baseContext)
            container.addView(surfaceView)
            mRtcEngine?.setupLocalVideo(
                VideoCanvas(
                    surfaceView,
                    VideoCanvas.RENDER_MODE_ADAPTIVE,
                    0
                )
            )
            mRtcEngine?.joinChannel(
                "65570d88c9374327838ee8a183ebd383", "srdrAkcay", null,
                1
            )
        }
    }

    private fun setupLocalVideo() {
        val container = binding.streamVideo
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView?.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_ADAPTIVE, 0))
    }
    private fun setupRemoteVideo(uid: Int) {
        val container = binding.streamVideo
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine?.setupRemoteVideo(
            VideoCanvas(
                surfaceView,
                VideoCanvas.RENDER_MODE_ADAPTIVE,
                uid
            )
        )
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            runOnUiThread {
                setupRemoteVideo(uid)
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            runOnUiThread {

            }
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            runOnUiThread {

            }
        }


        override fun onError(err: Int) {
            super.onError(err)
            runOnUiThread {

            }
        }

        override fun onConnectionLost() {
            super.onConnectionLost()
            runOnUiThread {

            }

        }

    }
}




