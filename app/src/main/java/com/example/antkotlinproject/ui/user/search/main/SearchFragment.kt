package com.example.antkotlinproject.ui.user.search.main

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.antkotlinproject.R
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

    private var categoryCode = CATEGORY

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
        setupSwipeRefresh()

        binding.btnCategoryBack.setOnClickListener { viewModel.fetchCategory() }
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
                return true
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchCourses()
            viewModel.fetchCategory()
        }

        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_green_light
        )
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.CategoriesFetched -> it.array?.let { it ->
                    categoryCode = CATEGORY
                    categoryAdapter.addItems(it)
                    binding.tvCategories.setText(R.string.search_by_categories)
                    binding.btnCategoryBack.visibility = View.GONE
                    binding.categoriesList.layoutManager = GridLayoutManager(requireContext(), 2)
                }
                is CourseEvent.CoursesFetched -> it.array?.let { it ->
                    courseAdapter.addItems(it)
                    viewModel.course = it
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is CategoryEvent.SubCategoryFetched -> it.item?.let { it ->
                    categoryCode = SUBCATEGORY
                    categoryAdapter.addItems(it.subCategories)
                    binding.tvCategories.text = it.name
                    binding.btnCategoryBack.visibility = View.VISIBLE
                    binding.categoriesList.layoutManager = GridLayoutManager(requireContext(), 1)
                }
            }
        })
    }

    override fun onCourseClick(item: CourseModel) {
        val directions =
            SearchFragmentDirections.actionSearchFragment2ToDetailCourseActivity(item.id!!, 0)
        findNavController().navigate(directions)
    }

    override fun onCategoryClick(item: CategoryModel) {
        if (categoryCode == SUBCATEGORY) {
            val directions =
                SearchFragmentDirections.actionSearchFragment2ToSubcategoryFragment( item.id )
            findNavController().navigate(directions)
        } else viewModel.fetchSubcategory(item.id)
    }

    companion object {
        private const val CATEGORY = 1
        private const val SUBCATEGORY = 2
    }
}

