package com.example.antkotlinproject.ui.user.bottomnavigation.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.model.getCategories
import com.example.antkotlinproject.data.model.getCourses
import com.example.antkotlinproject.databinding.FragmentSearchBinding
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.ui.auth.registration.CheckUserRegistrationFragmentDirections
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchFragment() : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    SearchViewModel::class
), ClickListener {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var courseAdapter: CourseAdapter

    override fun attachBinding(
        list: MutableList<FragmentSearchBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentSearchBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = SearchViewModel::class)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        setupCategories()
        setupCourses()
    }

    private fun setupCategories() {
        categoryAdapter = CategoryAdapter()
        binding.categoriesList.adapter = categoryAdapter
        viewModel.fetchCategory()
    }

    private fun setupCourses() {
        courseAdapter = CourseAdapter(this)
        binding.coursesList.adapter = courseAdapter
        viewModel.fetchCourses()
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.CategoryFetched -> it.array?.let { it -> categoryAdapter.addItems(it) }
            }
        })

        viewModel.event.observe(this, Observer {
            when (it) {
                is CourseEvent.CoursesFetched -> it.array?.let { it -> courseAdapter.addItems(it) }
            }
        })
    }

    override fun onItemClick(item: CourseModel) {
        findNavController().navigate(R.id.action_searchFragment2_to_detailCourseActivity)
    }
}

