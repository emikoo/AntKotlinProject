package com.example.antkotlinproject.ui.user.bottomnavigation.search.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.data.model.SubcategoryModel
import com.example.antkotlinproject.databinding.FragmentCategoriesBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class CategoriesFragment : BaseFragment<CategoriesViewModel, FragmentCategoriesBinding>(
    CategoriesViewModel::class
),
    SubcategoryClickListener {

    private lateinit var subcategoryAdapter: SubcategoryAdapter

    override fun attachBinding(
        list: MutableList<FragmentCategoriesBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentCategoriesBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        binding.btnLeft.setOnClickListener { findNavController().popBackStack() }
        setupViewModel()
        setupSubcategories()
    }

    private fun setupViewModel() {
        viewModel = getViewModel(clazz = CategoriesViewModel::class)
        val argument = CategoriesFragmentArgs.fromBundle(requireArguments())
        viewModel.fetchSubcategory(argument.categoryId)
    }

    private fun setupSubcategories() {
        subcategoryAdapter =
            SubcategoryAdapter(this)
        binding.subcategoriesList.adapter = subcategoryAdapter
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.SubCategoryFetched -> it.item?.let { it ->
                    binding.name.text = it.name
                    subcategoryAdapter.addItems(it.subCategories)
                }
            }
        })
    }

    override fun onSubcategoryClick(item: SubcategoryModel) {
        val id = item.id
        val direction =
            CategoriesFragmentDirections.actionCategoriesFragmentToSubcategoryFragment(id)
        findNavController().navigate(direction)
    }
}