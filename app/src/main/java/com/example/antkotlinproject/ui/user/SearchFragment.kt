package com.example.antkotlinproject.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentSearchBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel

class SearchFragment : BaseFragment<AuthorizationViewModel, FragmentSearchBinding>(
    AuthorizationViewModel::class
) {
    override fun setupViews() {}

    override fun subscribeToLiveData() {}

    override fun attachBinding(
        list: MutableList<FragmentSearchBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentSearchBinding.inflate(layoutInflater, container, attachToRoot))
    }

}