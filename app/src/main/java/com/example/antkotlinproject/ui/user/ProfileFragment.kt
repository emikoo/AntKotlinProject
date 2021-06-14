package com.example.antkotlinproject.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentProfileBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel

class ProfileFragment : BaseFragment<AuthorizationViewModel, FragmentProfileBinding>(
    AuthorizationViewModel::class
) {
    override fun setupViews() {
    }

    override fun subscribeToLiveData() {
    }

    override fun attachBinding(
        list: MutableList<FragmentProfileBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentProfileBinding.inflate(layoutInflater, container, attachToRoot))
    }

}