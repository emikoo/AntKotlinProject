package com.example.antkotlinproject.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentLoginBinding
import com.example.antkotlinproject.ui.base.BaseFragment
import com.example.antkotlinproject.utils.viewBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }
}