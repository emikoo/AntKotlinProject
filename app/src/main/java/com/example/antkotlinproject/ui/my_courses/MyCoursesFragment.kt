package com.example.antkotlinproject.ui.my_courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.FragmentMyCoursesBinding
import com.example.antkotlinproject.ui.user.subcategory.CourseAdapter
import com.example.antkotlinproject.ui.user.subcategory.CourseClickListener
import com.example.antkotlinproject.ui.user.subcategory.SubcategoryFragmentDirections
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyCoursesFragment : BaseFragment<DefaultViewModel, FragmentMyCoursesBinding>(
    DefaultViewModel::class
class MyCoursesFragment : BaseFragment<MyCoursesViewModel, FragmentMyCoursesBinding>(
    MyCoursesViewModel::class
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
        viewModel = getViewModel(clazz = MyCoursesViewModel::class)
        setupRecyclerView()
        setupSearchView()
        setupSwipeRefresh()
    }

    private fun setupRecyclerView() {
        adapter =
            CourseAdapter(
                this
            )
        binding.coursesList.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnSearchClickListener { binding.title.visibility = View.GONE }
        binding.searchView.setOnCloseListener {
            binding.title.visibility = View.VISIBLE
            false
        }
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when(it) {
                is CourseEvent.UserCoursesFetched -> it.array?.let {
                    viewModel.course = it
                    adapter.addItems(it)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    override fun onCourseClick(item: CourseModel) {
    }
}