package com.example.antkotlinproject.ui.my_courses

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.FragmentMyCoursesBinding
import com.example.antkotlinproject.utils.PrefsHelper
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MyCoursesFragment : BaseFragment<MyCoursesViewModel, FragmentMyCoursesBinding>(
    MyCoursesViewModel::class
), CourseClickListener {

    private lateinit var adapter: CourseAdapter
    private lateinit var prefsHelper: PrefsHelper

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
        viewModel.fetchUserCourses()
        setupRecyclerView()
        setupSearchView()
        setupSwipeRefresh()
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
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler().postDelayed(Runnable {
                    if (newText == "") {
                        viewModel.fetchUserCourses()
                    } else {
                        val searchText = newText?.toLowerCase()
                        val filteredName = mutableListOf<CourseModel>()
                        viewModel.course?.forEach {
                            if (searchText?.let { it1 -> it.name?.toLowerCase()?.contains(it1) }!!)
                                filteredName.add(it)
                        }
                        adapter.addItems(filteredName)
                    }
                }, 800)
                return false
                return true
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchUserCourses()
        }

        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_green_light
        )
    }

    override fun subscribeToLiveData() {
        viewModel.loading.observe(requireActivity(), Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
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
        prefsHelper = PrefsHelper(requireContext())
        if (!prefsHelper.getIsStuff()) {
            val directions =
                MyCoursesFragmentDirections.actionMyCoursesFragment2ToDetailCourseActivity(
                    0, 0, item.id!!)
            findNavController().navigate(directions)
        } else {
            findNavController().navigate(R.id.action_myCoursesFragment_to_studyCourseActivity)
        }
    }
}