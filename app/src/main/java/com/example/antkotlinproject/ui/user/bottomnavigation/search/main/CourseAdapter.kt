package com.example.antkotlinproject.ui.user.bottomnavigation.search.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.ItemCourseVerticalBinding
import com.example.antkotlinproject.databinding.ItemEmptyBinding
import com.example.antkotlinproject.utils.toLesson

class CourseAdapter(private val listenerCourse: CourseClickListener) : BaseAdapter() {
    private var coursesArray = mutableListOf<CourseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCourseVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingEmpty =
            ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == VIEW_TYPE_DATA) CourseViewHolder(binding)
        else EmptyViewHolder (bindingEmpty)
    }

    override fun getItemCount(): Int {
        return if (coursesArray.count() == 0) 1
        else coursesArray.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if (coursesArray.count() == 0) VIEW_TYPE_EMPTY
        else VIEW_TYPE_DATA
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == VIEW_TYPE_DATA) setupCourseViewHolder(holder as CourseViewHolder, position)
    }

    private fun setupCourseViewHolder(holder: CourseViewHolder, position: Int) {
        val item = coursesArray[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listenerCourse.onCourseClick(item)
        }
    }

    fun addItems(item: MutableList<CourseModel>) {
        coursesArray = item
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_DATA = 1
        const val VIEW_TYPE_EMPTY = 2
    }
}

class CourseViewHolder(var binding: ItemCourseVerticalBinding) : BaseViewHolder(binding.root) {
    fun bind(item: CourseModel) {
        Glide.with(binding.image.context)
            .load(item.coursePreviewImage)
            .placeholder(R.color.color_grey_transparent)
            .into(binding.image)
        binding.name.text = item.name
        binding.lessons.text = item.lessonsCount.toString().toLesson()
        binding.teacher.text = item.owner?.username
    }
}

class EmptyViewHolder(var binding: ItemEmptyBinding) : BaseViewHolder(binding.root)

interface CourseClickListener {
    fun onCourseClick(item: CourseModel)
}