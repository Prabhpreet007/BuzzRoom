package com.example.buzzroom

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.privacysandbox.tools.core.model.Type
import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomConfig
import com.zegocloud.uikit.prebuilt.liveaudioroom.ZegoUIKitPrebuiltLiveAudioRoomFragment

class LiveAudioRoomActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_live_audio_room)

        // Hide the system UI (status bar and navigation bar)
        hideSystemUI()

        val roomIdTextView = findViewById<TextView>(R.id.room_id_textview)
        roomIdTextView.text = "Room ID : " + intent.getStringExtra("roomId")

        addFragment()
    }

    private fun addFragment() {
        val appID: Long = KeyConstants.AppId
        val appSign = KeyConstants.AppSign
        val userID = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userId")

        val isHost = intent.getBooleanExtra("isHost", false)
        val roomID = intent.getStringExtra("roomId")

        val config: ZegoUIKitPrebuiltLiveAudioRoomConfig = if (isHost) {
            ZegoUIKitPrebuiltLiveAudioRoomConfig.host()
        } else {
            ZegoUIKitPrebuiltLiveAudioRoomConfig.audience()
        }
        val fragment = ZegoUIKitPrebuiltLiveAudioRoomFragment.newInstance(
            appID, appSign, userID, userName, roomID, config
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {
        window.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
}
}