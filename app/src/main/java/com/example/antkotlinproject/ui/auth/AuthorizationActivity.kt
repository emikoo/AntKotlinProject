package com.example.antkotlinproject.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.antkotlinproject.R
import kotlinx.android.synthetic.main.activity_authorization.*

class AuthorizationActivity : AppCompatActivity(), AuthorizationListener {
    private var host: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        host = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun openLoginFragment() {
        host?.navigate(R.id.action_authorizationFragment_to_loginFragment3)
    }

    override fun openCheckUserRegistrationFragment() {
        host?.navigate(R.id.checkUserRegistrationFragment)
    }
}