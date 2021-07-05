package com.example.antkotlinproject.ui.create_course

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAddBottomSheetFragment
import com.example.antkotlinproject.base.CategoryEvent
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.databinding.LayoutAddBottomSheetBinding
import com.example.antkotlinproject.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddCourseBottomSheetFragment() : BaseAddBottomSheetFragment() {

    private val viewModel by viewModel<AddCourseViewModel>()
    lateinit var binding: LayoutAddBottomSheetBinding

    private var categoryList = mutableListOf<String>("Выберите категорию", "Кулинария")

    private var categoryId: Int? = null
    private var subcategoryId: Int? = null
    private var previewImage: File? = null
    private var previewVideo: File? = null

    private val subcategoryList = mutableListOf<String>("Выберите подкатегорию", "Яблочный пирог")

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
        viewModel.fetchSubcategories()
        setupCategorySpinner()
        setupSubcategorySpinner()
        setupListener()
        setupViewModel()
    }

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
        binding.btnImage.setOnClickListener { pickPhotoFromGallery() }
        binding.btnVideo.setOnClickListener { pickVideoFromGallery() }
        createCourse()
        binding.toolbar.setNavigationOnClickListener { this.onDestroyView() }
    }

    override fun showPhoto(file: File) {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        binding.previewImage.setImageBitmap(bitmap)
        binding.btnImage.text = "Изменить"
        previewImage = file
    }

    override fun showVideo(file: File?) {
        binding.btnVideo.text = "Сменить видео"
        previewVideo = file
    }

    private fun createCourse() {
        binding.btnAdd.setOnClickListener {
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
                    showCongratsDialog()
                }
            }
        })
    }

    private fun showCongratsDialog() {
        val alert = AlertDialog.Builder(requireContext(), R.style.DialogStyle)
        val inflater = layoutInflater.inflate(R.layout.alert_congrats, null)
        alert.setView(inflater)
        val dialog = alert.create()
        dialog.show()
        Handler().postDelayed(Runnable {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }, 3000)
    }

    override fun showPhoto1(file: Uri?) {}
}