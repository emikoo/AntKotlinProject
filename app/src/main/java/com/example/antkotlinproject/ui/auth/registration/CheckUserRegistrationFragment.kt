package com.example.antkotlinproject.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentCheckUserRegistrationBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.base.BaseFragment

class CheckUserRegistrationFragment :
    BaseFragment<AuthorizationViewModel, FragmentCheckUserRegistrationBinding>(
        AuthorizationViewModel::class) {

    override fun attachBinding(
        list: MutableList<FragmentCheckUserRegistrationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean) {
        list.add(FragmentCheckUserRegistrationBinding.inflate( layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnPositive.setOnClickListener {
            findNavController().navigate(R.id.action_checkUserRegistrationFragment_to_registrationFragment2)
        }
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }

    override fun subscribeToLiveData() {}
}