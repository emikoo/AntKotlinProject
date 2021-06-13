package com.example.antkotlinproject.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.databinding.FragmentRegistrationBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.base.BaseFragment

class RegistrationFragment : BaseFragment<AuthorizationViewModel, FragmentRegistrationBinding>(
    AuthorizationViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentRegistrationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentRegistrationBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupListener()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
    }

    override fun subscribeToLiveData() {}
}