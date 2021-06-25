package com.example.antkotlinproject.ui.user.bottomnavigation.my_courses

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentMyCoursesBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel

class MyCoursesFragment : BaseFragment<DefaultViewModel, FragmentMyCoursesBinding>(
    DefaultViewModel::class
) {

    override fun attachBinding(
        list: MutableList<FragmentMyCoursesBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentMyCoursesBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
    }

    override fun subscribeToLiveData() {}
}