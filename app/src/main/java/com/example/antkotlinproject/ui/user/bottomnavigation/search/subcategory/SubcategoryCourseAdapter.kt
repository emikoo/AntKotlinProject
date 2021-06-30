package com.example.antkotlinproject.ui.user.bottomnavigation.search.subcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.model.SubcategoryCourseModel
import com.example.antkotlinproject.databinding.ItemCourseVerticalBinding
import com.example.antkotlinproject.utils.toLesson

class SubcategoryCourseAdapter(private val listenerCourse: CourseClickListener) : BaseAdapter() {
    private var coursesArray = mutableListOf<SubcategoryCourseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCourseVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return coursesArray.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = coursesArray[position]
        val holder = holder as CourseViewHolder
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listenerCourse.onCourseClick(item)
        }
    }

    fun addItems(item: MutableList<SubcategoryCourseModel>) {
        coursesArray = item
        notifyDataSetChanged()
    }
}

class CourseViewHolder(var binding: ItemCourseVerticalBinding) : BaseViewHolder(binding.root) {
    fun bind(item: SubcategoryCourseModel) {
        Glide.with(binding.image.context)
            .load(item.image)
            .placeholder(R.color.color_grey_transparent)
            .into(binding.image)
        binding.name.text = item.name
        binding.lessons.text = item.lessonsCount.toString().toLesson()
    }
}

interface CourseClickListener {
    fun onCourseClick(item: SubcategoryCourseModel)
}