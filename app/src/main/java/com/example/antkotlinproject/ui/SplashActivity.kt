package com.example.antkotlinproject.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.ActivitySplashBinding
import com.example.antkotlinproject.ui.auth.AuthorizationActivity
import com.example.antkotlinproject.utils.viewBinding

class SplashActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupAnimationText()
        setDelay()
    }

    private fun setupAnimationText() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashTitle.startAnimation(animation)
    }

    private fun setDelay() {
        Handler(Looper.getMainLooper()).postDelayed({ openActivity() }, 1500)
    }

    private fun openActivity() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }
}