package com.example.antkotlinproject.ui.user.bottomnavigation.search.main

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchFragment() : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    SearchViewModel::class
),
    CourseClickListener,
    CategoryClickListener {
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
        setupSearchView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchCourses()
        }

        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_green_light
        )
    }

    private fun setupRecyclerView() {
        setupCategories()
        setupCourses()
    }

    private fun setupCategories() {
        categoryAdapter =
            CategoryAdapter(
                this
            )
        binding.categoriesList.adapter = categoryAdapter
    }

    private fun setupCourses() {
        courseAdapter =
            CourseAdapter(
                this
            )
        binding.coursesList.adapter = courseAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler().postDelayed(Runnable {
                    if (newText == "") {
                        viewModel.fetchCourses()
                    } else {
                        val searchText = newText?.toLowerCase()
                        val filteredName = mutableListOf<CourseModel>()
                        viewModel.course?.forEach {
                            if (searchText?.let { it1 -> it.name?.toLowerCase()?.contains(it1) }!!)
                                filteredName.add(it)
                        }
                        courseAdapter.addItems(filteredName)
                    }
                }, 800)
                return false
            }
        })
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.CategoryFetched -> it.array?.let { it ->
                    categoryAdapter.addItems(it)
                }
                is CourseEvent.CoursesFetched -> it.array?.let { it ->
                    courseAdapter.addItems(it)
                    viewModel.course = it
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    override fun onCourseClick(item: CourseModel) {
        val directions =
            SearchFragmentDirections.actionSearchFragment2ToDetailCourseActivity(
                item.id!!
            )
        findNavController().navigate(directions)
    }

    override fun onCategoryClick(item: CategoryModel) {
        val categoryId = item.id
        val directions =
            SearchFragmentDirections.actionSearchFragment2ToCategoriesFragment(
                categoryId
            )
        findNavController().navigate(directions)
    }
}

