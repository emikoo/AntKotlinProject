package com.example.antkotlinproject.ui.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.LessonModel
import com.example.antkotlinproject.databinding.ItemEmptyBinding
import com.example.antkotlinproject.databinding.ItemLessonBinding
import com.example.antkotlinproject.ui.my_courses.CourseAdapter
import com.example.antkotlinproject.ui.my_courses.CourseViewHolder
import com.example.antkotlinproject.ui.my_courses.EmptyViewHolder

class LessonsAdapter : BaseAdapter() {

    val lessonsArray = mutableListOf<LessonModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingEmpty =
            ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == CourseAdapter.VIEW_TYPE_DATA) LessonsViewHolder(binding)
        else EmptyViewHolder(bindingEmpty)
    }

    override fun getItemCount(): Int {
        return if (lessonsArray.count() == 0) 1
        else lessonsArray.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if (lessonsArray.count() == 0) VIEW_TYPE_DATA
        else VIEW_TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val type = getItemViewType(position)
        if (type == VIEW_TYPE_DATA) setupLessonViewHolder(holder as LessonsViewHolder, position)
    }

    private fun setupLessonViewHolder(holder: LessonsViewHolder, position: Int) {

    }

    companion object {
        const val VIEW_TYPE_DATA = 1
        const val VIEW_TYPE_EMPTY = 2
    }
}

class LessonsViewHolder(var binding: ItemLessonBinding) : BaseViewHolder(binding.root) {
    fun bind(item: LessonModel) {

    }
}

class EmptyViewHolder(var binding: ItemEmptyBinding) : BaseViewHolder(binding.root) {
    fun bind() {
        binding.title.text = "У вас нет уроков"
    }
}