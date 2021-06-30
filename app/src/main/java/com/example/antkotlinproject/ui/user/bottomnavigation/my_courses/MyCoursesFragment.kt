package com.example.antkotlinproject.ui.user.bottomnavigation.my_courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.FragmentMyCoursesBinding
import com.example.antkotlinproject.ui.auth.DefaultViewModel
import com.example.antkotlinproject.ui.user.bottomnavigation.search.main.CourseAdapter
import com.example.antkotlinproject.ui.user.bottomnavigation.search.main.CourseClickListener

class MyCoursesFragment : BaseFragment<DefaultViewModel, FragmentMyCoursesBinding>(
    DefaultViewModel::class
), CourseClickListener {

    private lateinit var adapter: CourseAdapter

    override fun attachBinding(
        list: MutableList<FragmentMyCoursesBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentMyCoursesBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        adapter = CourseAdapter(this)
        binding.coursesList.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnSearchClickListener { binding.title.visibility = View.GONE }
        binding.searchView.setOnCloseListener {
            binding.title.visibility = View.VISIBLE
            false
        }
    }

    override fun subscribeToLiveData() {}

    override fun onCourseClick(item: CourseModel) {
    }
}