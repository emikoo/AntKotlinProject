package com.example.antkotlinproject.ui.user.detail_course

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.VideoPlayerActivity
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.databinding.ActivityDetailCourseBinding
import com.example.antkotlinproject.utils.toLesson
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailCourseActivity : BaseActivity<DetailCourseViewModel, ActivityDetailCourseBinding>(
    DetailCourseViewModel::class
) {
    override fun getViewBinding() = ActivityDetailCourseBinding.inflate(layoutInflater)

    override fun setupViews() {
        viewModel = getViewModel(clazz = DetailCourseViewModel::class)
        fetchCourse()
        setupListener()
    }

    private fun fetchCourse() {
        val arguments: DetailCourseActivityArgs by navArgs<DetailCourseActivityArgs>()
        viewModel.fetchCourse(arguments.courseId)
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener { this.onBackPressed() }
        binding.videoPlayer.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is CourseEvent.CourseFetched -> it.item?.let { it ->
                    binding.toolbar.title = it.name
                    Glide.with(binding.previewImage)
                        .load(it.coursePreviewImage)
                        .placeholder(R.color.color_grey_transparent)
                        .into(binding.previewImage)
                    binding.lessons.text = it.lessonsCount.toString().toLesson()
                    binding.tvDescription.text = it.description
                    binding.teacherName.text = it.owner?.username
                    Glide.with(binding.teacherPhoto)
                        .load(it.owner?.avatar)
                        .placeholder(R.color.color_grey_transparent)
                        .into(binding.teacherPhoto)
                }
            }
        })
    }
}