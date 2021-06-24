package com.example.antkotlinproject.ui.user.teacher_s_profile

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.databinding.ActivityTeacherProfileBinding
import com.example.antkotlinproject.ui.user.bottomnavigation.profile.ProfileViewModel
import com.example.antkotlinproject.ui.user.detail_course.DetailCourseActivity.Companion.OWNER
import com.example.antkotlinproject.utils.showAlertDialog
import com.example.antkotlinproject.utils.toAt
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TeacherProfileActivity : BaseActivity<ProfileViewModel, ActivityTeacherProfileBinding>(
    ProfileViewModel::class
) {
    override fun getViewBinding() = ActivityTeacherProfileBinding.inflate(layoutInflater)

    override fun setupViews() {
        viewModel = getViewModel(clazz = ProfileViewModel::class)
        val teacherId = intent.getIntExtra(OWNER, 0)
        viewModel.fetchTeacherProfile(teacherId)
        setupListener()
    }

    private fun setupListener() {
        binding.btnClose.setOnClickListener { this.onBackPressed() }
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is ProfileEvent.TeacherProfileFetched -> it.let {
                    binding.email.text = it.item?.email
                    binding.name.text = "${it.item?.firstName} ${it.item?.lastName}"
                    binding.username.text = it.item?.username?.toAt()
                    Glide.with(binding.photo)
                        .load(it.item?.avatar)
                        .into(binding.photo)
                    binding.phone.text = it.item?.phone.toString()
                    binding.courses.text =
                        "Еще курсы от ${it.item?.firstName} ${it.item?.lastName}:"
                }
            }
        })
    }
}