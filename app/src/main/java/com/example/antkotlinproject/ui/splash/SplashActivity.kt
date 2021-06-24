package com.example.antkotlinproject.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.databinding.ActivitySplashBinding
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationActivity
import com.example.antkotlinproject.ui.user.main.MainUserActivity
import com.example.antkotlinproject.utils.PrefsHelper

class SplashActivity : BaseActivity<AuthViewModel, ActivitySplashBinding>
    (AuthViewModel::class) {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)
    private lateinit var preferences: PrefsHelper

    override fun setupViews() {
        preferences = PrefsHelper(this)
        setupAnimationViews()
        setupDelay()
    }

    private fun setupAnimationViews() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.splashTitle.startAnimation(animation)
        binding.splashLogo.startAnimation(animation)
    }

    private fun setupDelay() {
        if (preferences.getToken()
                .isEmpty()
        ) Handler(Looper.getMainLooper()).postDelayed({ openAuthorization() }, 1500)
        else Handler(Looper.getMainLooper()).postDelayed({ openMainUserActivity() }, 1500)
    }

    private fun openAuthorization() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openMainUserActivity() {
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun subscribeToLiveData() {}
}