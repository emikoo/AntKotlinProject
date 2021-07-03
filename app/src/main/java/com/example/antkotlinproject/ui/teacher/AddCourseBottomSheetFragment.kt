package com.example.antkotlinproject.ui.teacher

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.antkotlinproject.base.BaseAddBottomSheetFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.pickPhotoFromGalleryWithPermissionCheck
import com.example.antkotlinproject.databinding.LayoutAddBottomSheetBinding
import com.example.antkotlinproject.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddCourseBottomSheetFragment() : BaseAddBottomSheetFragment() {

    private val viewModel by viewModel<AddCourseViewModel>()
    lateinit var binding: LayoutAddBottomSheetBinding

    private var categoryList = mutableListOf<String>("Выберите категорию")

    private var categoryId: Int? = null
    private var subcategoryId: Int? = null
    private var previewImage: File? = null

    private val subcategoryList = mutableListOf<String>("Выберите подкатегорию")
    private var filteredList = mutableListOf<String>()
    private var oldList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutAddBottomSheetBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun setupViews() {
        setupCategorySpinner()
        setupSubCategorySpinner()
        setupListener()
        setupViewModel()
    }

    private fun setupCategorySpinner() {
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categoryList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter
        getSubcategoriesOfCategory()
    }

    private fun getSubcategoriesOfCategory() {
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoryId = parent?.getItemIdAtPosition(position).toString().toInt()
                viewModel.fetchSubcategory(categoryId!!)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun setupSubCategorySpinner() {
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, subcategoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spSubcategory.adapter = adapter
        binding.spSubcategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                subcategoryId = p0?.getItemIdAtPosition(p2).toString().toInt()
            }

        }
    }

    private fun setupListener() {
        binding.btnImage.setOnClickListener { pickPhotoFromGalleryWithPermissionCheck() }
        createCourse()
        binding.toolbar.setNavigationOnClickListener { this.onDestroyView() }
    }

    override fun showPhoto(file: File) {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        binding.previewImage.setImageBitmap(bitmap)
        binding.btnImage.text = "Изменить"
        previewImage = file
    }

    private fun createCourse() {
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val lessonsCount = binding.etLessonsCount.text.toString()
            val description = binding.etDescription.text.toString()
            val price = binding.etPrice.text.toString()
            if (name.isNotEmpty() && lessonsCount.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()
                && categoryId != 0 && previewImage != null) {
                viewModel.createCourse(
                    name = name, description = description, categoryId = categoryId!!.toInt(),
                    lessonsCount = lessonsCount.toInt(), subcategoryId = subcategoryId!!,
                    price = price.toDouble(), previewImage = previewImage!!
                )
            } else showToast(requireActivity(), "Заполните все поля")
        }
    }

    private fun setupViewModel() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.CategoriesFetched -> it.array?.let { it ->
                    for (array in it) {
                        val category = array.name
                        categoryList.add(category)
                    }
                }
                is CategoryEvent.SubCategoryFetched -> it.item?.let { it ->
                    subcategoryList.removeAll(oldList)
                    oldList.clear()
                    for (array in it.subCategories) {
                        val subcategory = array.name
                        filteredList.add(subcategory)
                        subcategoryList.addAll(filteredList)
                        oldList.addAll(filteredList)
                        filteredList.clear()
                    }
                }
            }
        })
    }

    override fun showPhoto1(file: Uri?) {}
}