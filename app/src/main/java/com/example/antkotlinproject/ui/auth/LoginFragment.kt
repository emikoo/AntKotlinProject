package com.example.antkotlinproject.ui.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.databinding.FragmentLoginBinding
import com.example.antkotlinproject.ui.base.BaseFragment

class LoginFragment : BaseFragment<AuthorizationViewModel, FragmentLoginBinding>(
    AuthorizationViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentLoginBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean) {
        list.add(FragmentLoginBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupListener()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }

    override fun subscribeToLiveData() {}
}