package com.example.antkotlinproject.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentMyCoursesBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel

class MyCoursesFragment : BaseFragment<AuthorizationViewModel, FragmentMyCoursesBinding>(
    AuthorizationViewModel::class
) {
    override fun setupViews() {
    }

    override fun subscribeToLiveData() {
    }

    override fun attachBinding(
        list: MutableList<FragmentMyCoursesBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentMyCoursesBinding.inflate(layoutInflater, container, attachToRoot))
    }

}