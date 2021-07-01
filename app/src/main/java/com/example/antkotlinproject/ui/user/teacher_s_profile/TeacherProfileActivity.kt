package com.example.antkotlinproject.ui.user.teacher_s_profile

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.databinding.ActivityTeacherProfileBinding
import com.example.antkotlinproject.ui.profile.ProfileViewModel
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
        setupSwipeRefresh(teacherId)
    }

    private fun setupListener() {
        binding.btnClose.setOnClickListener { this.onBackPressed() }
        binding.btnPhoneCall.setOnClickListener { showAlertDialog(this, this::makeCall) }
    }

    private fun makeCall() {
        val phoneNumber = binding.phone.text
        if (!TextUtils.isEmpty(phoneNumber)) {
            val dial = "tel:0$phoneNumber"
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(dial)))
        }
    }

    private fun setupSwipeRefresh(teacherId: Int) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchTeacherProfile(teacherId)
        }
        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_green_light
        )
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
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }
}