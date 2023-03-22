package com.example.antkotlinproject.ui.create_course

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAddBottomSheetFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.databinding.LayoutAddBottomSheetBinding
import com.example.antkotlinproject.ui.create_course.CameraOrGalleryBottomSheet.Companion.FILE
import com.example.antkotlinproject.utils.showCongratsDialog
import com.example.antkotlinproject.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddCourseBottomSheetFragment() : BottomSheetDialogFragment() {

    private val viewModel by viewModel<AddCourseViewModel>()
    lateinit var binding: LayoutAddBottomSheetBinding

    private var previewImage: File? = null
    private var previewVideo: File? = null

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireActivity(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        viewModel.fetchSubcategories()
        setupCategorySpinner()
        setupSubcategorySpinner()
        setupListener()
        setupViewModel()
    }

    private var categoryList = mutableListOf<String>("Выберите категорию")
    private val subcategoryList = mutableListOf<String>("Выберите подкатегорию")
    private var categoryId: Int? = null
    private var subcategoryId: Int? = null

    private fun setupCategorySpinner() {
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categoryList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = adapter
        binding.spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                categoryId = p0?.getItemIdAtPosition(p2).toString().toInt()
            }
        }
    }

    private fun setupSubcategorySpinner() {
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
        createCourse()
        binding.toolbar.setNavigationOnClickListener { this.onDestroyView() }
        binding.btnImage.setOnClickListener { openBottomSheetDialog(PHOTO) }
        binding.btnVideo.setOnClickListener { openBottomSheetDialog(VIDEO) }
    }

    private fun openBottomSheetDialog(type: Int) {
        val bottomSheetDialogFragment: BottomSheetDialogFragment =
            CameraOrGalleryBottomSheet()
        bottomSheetDialogFragment.isCancelable = true
        bottomSheetDialogFragment.setTargetFragment(this, type)
        bottomSheetDialogFragment.show(
            requireActivity().supportFragmentManager,
            bottomSheetDialogFragment.tag
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val file = data?.getSerializableExtra(FILE) as File
                previewImage = file
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                binding.previewImage.setImageBitmap(bitmap)
                binding.btnImage.text = "Изменить"
            } else if (resultCode == Activity.RESULT_CANCELED) { }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                val file = data?.getSerializableExtra(FILE) as File
                previewVideo = file
                binding.btnVideo.text = "Сменить видео"
            } else if (resultCode == Activity.RESULT_CANCELED) { }
        }
    }

    private fun createCourse() {
        binding.btnAdd.setOnClickListener {
            showCongratsDialog(requireContext(), layoutInflater, R.string.course_created)
            val name = binding.etName.text.toString()
            val lessonsCount = binding.etLessonsCount.text.toString()
            val description = binding.etDescription.text.toString()
            val price = binding.etPrice.text.toString()
            if (name.isNotEmpty() && lessonsCount.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()
                && categoryId != 0 && previewImage != null && previewVideo != null && subcategoryId != null
            ) {
                viewModel.createCourse(
                    name = name, description = description, categoryId = categoryId!!.toInt(),
                    lessonsCount = lessonsCount.toInt(), subcategoryId = subcategoryId!!,
                    price = price.toDouble(), previewImage = previewImage!!, video = previewVideo!!
                )
            } else showToast(requireActivity(), "Заполните все поля")
        }
    }

    private fun setupViewModel() {
        viewModel.loading.observe(this, Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
        viewModel.event.observe(this, Observer {
            when (it) {
                is CategoryEvent.CategoriesFetched -> it.array?.let { it ->
                    for (array in it) {
                        val category = array.name
                        categoryList.add(category)
                    }
                }
                is CategoryEvent.SubcategoriesFetched -> it.array?.let { it ->
                    for (array in it) subcategoryList.add(array.name)
                }
                is CourseEvent.CourseCreated -> {
                    viewModel.createAccessCourse(it.item.owner!!.id!!, it.item.id!!)
                    this.dismiss()
                    showCongratsDialog(requireContext(), layoutInflater, R.string.course_created)
                }
            }
        })
    }

    companion object {
        const val PHOTO = 1
        const val VIDEO = 2
    }
}