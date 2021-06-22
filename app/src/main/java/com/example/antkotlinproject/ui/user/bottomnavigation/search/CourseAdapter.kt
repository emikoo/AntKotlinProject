package com.example.antkotlinproject.ui.user.bottomnavigation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseAdapter
import com.example.antkotlinproject.base.BaseViewHolder
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.databinding.ItemCourseVerticalBinding

class CourseAdapter(private val listener: ClickListener): BaseAdapter() {
    private var coursesArray = mutableListOf<CourseModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding =
            ItemCourseVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coursesArray.count()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = coursesArray[position]
        val holder = holder as CourseViewHolder
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    fun addItems(item: MutableList<CourseModel>) {
        coursesArray = item
        notifyDataSetChanged()
    }
}

class CourseViewHolder(var binding: ItemCourseVerticalBinding) : BaseViewHolder(binding.root) {
    fun bind(item: CourseModel) {
        Glide.with(binding.image.context)
            .load(item.coursePreviewImage)
            .placeholder(R.color.color_grey_transparent)
            .into(binding.image)
        binding.name.text = item.name
        binding.lessons.text = item.lessonsCount.toString()
        binding.teacher.text = item.owner?.username
    }
}

interface ClickListener{
    fun onItemClick(item: CourseModel)
}