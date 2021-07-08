package com.example.antkotlinproject.ui.teacher.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentLessonsBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.my_courses.MyCoursesViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LessonsFragment : BaseFragment<DefaultViewModel, FragmentLessonsBinding>(
    DefaultViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentLessonsBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentLessonsBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = DefaultViewModel::class)
    }

    override fun subscribeToLiveData() {}
}