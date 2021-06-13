package com.example.antkotlinproject.ui.user

import com.example.antkotlinproject.databinding.ActivityMainBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.base.BaseActivity

class MainUserActivity : BaseActivity<AuthorizationViewModel, ActivityMainBinding>(
    AuthorizationViewModel::class
) {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {}

    override fun subscribeToLiveData() {}
}