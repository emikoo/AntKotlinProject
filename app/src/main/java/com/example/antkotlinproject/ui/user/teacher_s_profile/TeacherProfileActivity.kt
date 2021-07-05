package com.example.antkotlinproject.ui.user.teacher_s_profile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.antkotlinproject.base.BaseActivity
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.databinding.ActivityTeacherProfileBinding
import com.example.antkotlinproject.ui.profile.ProfileViewModel
import com.example.antkotlinproject.ui.detail_course.DetailCourseActivity.Companion.OWNER
import com.example.antkotlinproject.utils.showAlertDialog
import com.example.antkotlinproject.utils.showToast
import com.example.antkotlinproject.utils.toAt
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TeacherProfileActivity : BaseActivity<TeacherProfileViewModel, ActivityTeacherProfileBinding>(
    TeacherProfileViewModel::class
) {
    override fun getViewBinding() = ActivityTeacherProfileBinding.inflate(layoutInflater)

    override fun setupViews() {
        setupViewModel()
        setupListener()
    }

    private fun setupViewModel() {
        viewModel = getViewModel(clazz = TeacherProfileViewModel::class)
        val teacherId = intent.getIntExtra(OWNER, 0)
        viewModel.fetchTeacherProfile(teacherId)
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

    @SuppressLint("SetTextI18n")
    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is ProfileEvent.TeacherProfileFetched -> {
                    binding.email.text = it.item.email
                    binding.name.text = "${it.item.firstName} ${it.item.lastName}"
                    Glide.with(binding.photo)
                        .load(it.item.avatar)
                        .into(binding.photo)
                    binding.phone.text = it.item.phone.toString()
                    binding.courses.text =
                        "Еще курсы от ${it.item.firstName} ${it.item.lastName}:"
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
        viewModel.loading.observe(this, Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }
}