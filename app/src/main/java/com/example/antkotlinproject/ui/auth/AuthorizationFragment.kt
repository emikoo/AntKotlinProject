package com.example.antkotlinproject.ui.auth

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.example.antkotlinproject.R
import com.example.antkotlinproject.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_authorization.*

class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {
    private var listener: AuthorizationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as AuthorizationListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sign_up.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setupListeners()
    }

    private fun setupListeners() {
        btn_enter.setOnClickListener {
            listener?.openLoginFragment()
        }
    }
}