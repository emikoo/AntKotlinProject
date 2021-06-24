package com.example.antkotlinproject.ui.auth

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.BaseEvent
import com.example.antkotlinproject.base.BaseViewModel
import com.example.antkotlinproject.databinding.ActivityAuthorizationBinding

class DefaultViewModel : BaseViewModel<BaseEvent>()

class AuthorizationActivity : BaseActivity<DefaultViewModel, ActivityAuthorizationBinding>(
    DefaultViewModel::class
) {
    override fun getViewBinding() = ActivityAuthorizationBinding.inflate(layoutInflater)
    private var host: NavController? = null

    override fun setupViews() {
        host = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun subscribeToLiveData() {}
}