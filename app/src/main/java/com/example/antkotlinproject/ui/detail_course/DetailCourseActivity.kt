package com.example.antkotlinproject.ui.detail_course

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.CourseEvent
import com.example.antkotlinproject.databinding.ActivityDetailCourseBinding
import com.example.antkotlinproject.ui.exo_player.VideoPlayerActivity
import com.example.antkotlinproject.ui.user.teacher_s_profile.TeacherProfileActivity
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.antkotlinproject.utils.toLesson
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailCourseActivity : BaseActivity<DetailCourseViewModel, ActivityDetailCourseBinding>(
    DetailCourseViewModel::class
) {
    override fun getViewBinding() = ActivityDetailCourseBinding.inflate(layoutInflater)

    lateinit var prefsHelper: PrefsHelper

    var ownerId: Int? = null

    override fun setupViews() {
        viewModel = getViewModel(clazz = DetailCourseViewModel::class)
        fetchCourse()
        setupListener()
    }

    private fun fetchCourse() {
        prefsHelper = PrefsHelper(this)

        val arguments: DetailCourseActivityArgs by navArgs()

        val searchArgs = arguments.searchCourseId
        val subcategoryArgs = arguments.subcategoryCourseId
        val myUserCourseArgs = arguments.myUserCourseId

        if (subcategoryArgs != 0) viewModel.fetchCourse(subcategoryArgs)
        else if (searchArgs != 0) viewModel.fetchCourse(searchArgs)
        else if (myUserCourseArgs != 0) viewModel.fetchCourse(myUserCourseArgs)
    }

    private fun setupListener() {
        binding.toolbar.setNavigationOnClickListener { this.onBackPressed() }
        openTeacherProfile()
    }

    override fun subscribeToLiveData() {
        viewModel.loading.observe(this, Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
        viewModel.event.observe(this, Observer {
            when (it) {
                is CourseEvent.CourseFetched -> it.item?.let { it ->
                    binding.toolbar.title = it.name
                    Glide.with(binding.previewImage)
                        .load(it.coursePreviewVideo)
                        .placeholder(R.color.color_grey_transparent)
                        .into(binding.previewImage)
                    binding.lessons.text = it.lessonsCount.toString().toLesson()
                    binding.tvDescription.text = it.description
                    binding.teacherName.text = "${it.owner?.firstName} ${it.owner?.lastName}"
                    Glide.with(binding.teacherPhoto)
                        .load(it.owner?.avatar)
                        .placeholder(R.color.color_grey_transparent)
                        .into(binding.teacherPhoto)
                    val video = it.coursePreviewVideo
                    showVideo(video)
                    binding.btnStart.text = it.price?.toInt().toString()
                    ownerId = it.owner?.id
                }
            }
        })
    }

    private fun showVideo(video: String?) {
        binding.videoPlayer.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(VIDEO, video)
            startActivity(intent)
        }
    }

    private fun openTeacherProfile() {
        binding.btnTeacher.setOnClickListener {
            val intent = Intent(this, TeacherProfileActivity::class.java)
            intent.putExtra(OWNER, ownerId)
            startActivity(intent)
        }
    }

    companion object {
        const val VIDEO = "VIDEO"
        const val OWNER = "OWNER"
    }
}