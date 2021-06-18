package com.example.antkotlinproject.ui.user.bottomnavigation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.data.model.getCategories
import com.example.antkotlinproject.data.model.getCourses
import com.example.antkotlinproject.databinding.FragmentSearchBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel

class SearchFragment : BaseFragment<AuthorizationViewModel, FragmentSearchBinding>(
    AuthorizationViewModel::class
) {
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var courseAdapter: CourseAdapter

    override fun attachBinding(
        list: MutableList<FragmentSearchBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentSearchBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        setupCategories()
        setupCourses()
    }

    private fun setupCategories() {
        categoryAdapter = CategoryAdapter()
        binding.categoriesList.adapter = categoryAdapter
        categoryAdapter.addItems(getCategories())
    }

    private fun setupCourses() {
        courseAdapter = CourseAdapter()
        binding.coursesList.adapter = courseAdapter
        courseAdapter.addItems(getCourses())
    }

    override fun subscribeToLiveData() {}
}