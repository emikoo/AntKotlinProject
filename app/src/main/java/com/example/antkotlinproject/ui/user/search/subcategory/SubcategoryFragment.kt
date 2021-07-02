package com.example.antkotlinproject.ui.user.search.subcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.data.model.SubcategoryCourseModel
import com.example.antkotlinproject.databinding.FragmentSubcategoryBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SubcategoryFragment : BaseFragment<SubcategoryViewModel, FragmentSubcategoryBinding>(
    SubcategoryViewModel::class
), CourseClickListener {

    lateinit var adapter: SubcategoryCourseAdapter

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
        adapter = SubcategoryCourseAdapter(this)
        binding.coursesList.adapter = adapter
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CourseEvent.SubcategoryCoursesFetched -> it.item?.let { it ->
                    adapter.addItems(it.courses)
                    binding.name.text = it.name
                }
            }
        })
    }

    override fun onCourseClick(item: SubcategoryCourseModel) {

    }
}