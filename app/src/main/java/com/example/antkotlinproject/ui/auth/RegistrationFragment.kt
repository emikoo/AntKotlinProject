package com.example.antkotlinproject.ui.auth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentRegistrationBinding
import com.example.antkotlinproject.ui.base.BaseFragment
import com.example.antkotlinproject.utils.viewBinding

class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {
    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }
}