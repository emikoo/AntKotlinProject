package com.example.antkotlinproject.ui.auth

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentAuthorizationBinding
import com.example.antkotlinproject.ui.base.BaseFragment
import com.example.antkotlinproject.utils.viewBinding

class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {
    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_loginFragment33)
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_checkUserRegistrationFragment2)
        }
    }
}