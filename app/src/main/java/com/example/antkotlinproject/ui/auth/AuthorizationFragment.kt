package com.example.antkotlinproject.ui.auth

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentAuthorizationBinding
import com.example.antkotlinproject.ui.base.BaseFragment

class AuthorizationFragment :
    BaseFragment<AuthorizationViewModel, FragmentAuthorizationBinding>(
        AuthorizationViewModel::class
    ) {
    override fun attachBinding(
        list: MutableList<FragmentAuthorizationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentAuthorizationBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
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

    override fun subscribeToLiveData() {}
}