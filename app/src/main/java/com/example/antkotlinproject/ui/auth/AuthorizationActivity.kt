package com.example.antkotlinproject.ui.auth

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.ActivityAuthorizationBinding
import com.example.antkotlinproject.ui.base.BaseActivity
import com.example.antkotlinproject.ui.base.BaseViewModel

class AuthorizationViewModel : BaseViewModel()

class AuthorizationActivity : BaseActivity<AuthorizationViewModel, ActivityAuthorizationBinding>(
    AuthorizationViewModel::class
) {
    override fun getViewBinding() = ActivityAuthorizationBinding.inflate(layoutInflater)
    private var host: NavController? = null

    override fun setupViews() {
        host = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun subscribeToLiveData() {}
}