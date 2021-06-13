package com.example.antkotlinproject.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.ActivitySplashBinding
import com.example.antkotlinproject.ui.auth.AuthorizationActivity
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel

class SplashViewModel : BaseViewModel<BaseEvent>()

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>
    (SplashViewModel::class) {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupAnimationText()
        Handler(Looper.getMainLooper()).postDelayed({ openActivity() }, 1500)
    }

    private fun setupAnimationText() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashTitle.startAnimation(animation)
    }

    private fun openActivity() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun subscribeToLiveData() {}
}