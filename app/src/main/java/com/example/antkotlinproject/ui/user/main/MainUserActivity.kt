package com.example.antkotlinproject.ui.user.main

import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.databinding.ActivityMainUserBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainUserActivity : BaseActivity<DefaultViewModel, ActivityMainUserBinding>(
    DefaultViewModel::class
) {
    override fun getViewBinding() = ActivityMainUserBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupBottomView()
    }

    private fun setupBottomView() {
        val navController =
            Navigation.findNavController(this, R.id.main_user_nav_view)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun subscribeToLiveData() {}

}