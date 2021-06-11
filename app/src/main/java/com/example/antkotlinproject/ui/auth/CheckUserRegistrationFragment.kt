package com.example.antkotlinproject.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentCheckUserRegistrationBinding
import com.example.antkotlinproject.ui.base.BaseFragment
import com.example.antkotlinproject.utils.viewBinding

class CheckUserRegistrationFragment : BaseFragment(R.layout.fragment_check_user_registration) {
    private val binding by viewBinding(FragmentCheckUserRegistrationBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnPositive.setOnClickListener {
            findNavController().navigate(R.id.action_checkUserRegistrationFragment_to_registrationFragment2)
        }
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }
}