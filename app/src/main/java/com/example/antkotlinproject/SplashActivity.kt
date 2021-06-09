package com.example.antkotlinproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupAnimationText()
        setDelay()
    }

    private fun setupAnimationText() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        splash_title.startAnimation(animation)
    }

    private fun setDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            openActivity()
        }, 1500)
    }

    private fun openActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}