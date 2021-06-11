package com.example.antkotlinproject.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.antkotlinproject.R

class AuthorizationActivity : AppCompatActivity() {
    private var host: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        host = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}