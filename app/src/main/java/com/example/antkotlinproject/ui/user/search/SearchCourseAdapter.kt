package com.example.antkotlinproject.ui.user.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.ItemCoursesHorizontalBinding
import com.example.antkotlinproject.utils.toLesson
import com.example.antkotlinproject.utils.toSom

class SearchCourseAdapter(private val listener: SearchCourseClickListener): BaseAdapter() {

    private var courseArray = mutableListOf<CourseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCoursesHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseHorizontalViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return courseArray.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = courseArray[position]
        val holder = holder as CourseHorizontalViewHolder
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onCourseClick(item)
        }
    }

    fun addItems(item: MutableList<CourseModel>) {
        courseArray = item
        notifyDataSetChanged()
    }
}

class CourseHorizontalViewHolder(val binding: ItemCoursesHorizontalBinding): BaseViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(item: CourseModel) {
        binding.name.text = item.name.toString()
        binding.lessons.text = item.lessonsCount.toString().toLesson()
        binding.teacher.text = "${item.owner?.firstName} ${item.owner?.lastName}"
        binding.price.text = item.price?.toInt().toString().toSom()
        Glide.with(binding.image.context)
            .load(item.coursePreviewImage)
            .placeholder(R.color.color_grey)
            .into(binding.image)
    }
}

interface SearchCourseClickListener {
    fun onCourseClick(item: CourseModel)
}