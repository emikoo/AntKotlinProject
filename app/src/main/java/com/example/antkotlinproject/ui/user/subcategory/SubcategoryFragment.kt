package com.example.antkotlinproject.ui.user.subcategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.FragmentSubcategoryBinding
import com.example.antkotlinproject.ui.my_courses.CourseAdapter
import com.example.antkotlinproject.ui.my_courses.CourseClickListener
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SubcategoryFragment : BaseFragment<SubcategoryViewModel, FragmentSubcategoryBinding>(
    SubcategoryViewModel::class
), CourseClickListener {

    lateinit var adapter: CourseAdapter

    override fun attachBinding(
        list: MutableList<FragmentSubcategoryBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentSubcategoryBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = SubcategoryViewModel::class)
        val argument = SubcategoryFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchSubcategoryCourses(argument.subcategoryId)
        setupRecyclerView()
        binding.btnLeft.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupRecyclerView() {
        adapter =
            CourseAdapter(
                this
            )
        binding.coursesList.adapter = adapter
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CourseEvent.SubcategoryCoursesFetched -> it.item?.let { it ->
                    adapter.addItems(it.courses!!)
                    binding.name.text = it.name
                }
            }
        })
        viewModel.loading.observe(requireActivity(), Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }

    override fun onCourseClick(item: CourseModel) {
        val directions =
            SubcategoryFragmentDirections.actionSubcategoryFragmentToDetailCourseActivity(0, item.id!!, 0)
        findNavController().navigate(directions)
    }
}