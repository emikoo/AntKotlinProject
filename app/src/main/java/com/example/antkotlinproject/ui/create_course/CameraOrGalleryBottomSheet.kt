package com.example.antkotlinproject.ui.create_course

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.*
import com.example.antkotlinproject.databinding.LayoutCameraOrGalleryBottomBinding
import com.example.antkotlinproject.ui.create_course.AddCourseBottomSheetFragment.Companion.PHOTO
import com.example.antkotlinproject.ui.create_course.AddCourseBottomSheetFragment.Companion.VIDEO
import java.io.File
import java.io.Serializable

class CameraOrGalleryBottomSheet: BaseAddBottomSheetFragment() {

    lateinit var binding: LayoutCameraOrGalleryBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutCameraOrGalleryBottomBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        checkPhotoOfVideo(this::setupViewsForPhoto, this::setupViewsForVideo)
        setupListener()
    }

    private fun checkPhotoOfVideo(actionPhoto: () -> Unit, actionVideo: () -> Unit) {
        if (this.targetRequestCode == VIDEO) { actionVideo() }
        else if (this.targetRequestCode == PHOTO) { actionPhoto() }
    }

    private fun setupViewsForPhoto() {
        binding.btnGallery.setText(R.string.choose_photo)
        binding.btnCamera.setText(R.string.camera)
    }

    private fun setupViewsForVideo() {
        binding.btnGallery.setText(R.string.choose_video)
        binding.btnCamera.setText(R.string.video)
    }

    private fun setupListener() {
        binding.btnGallery.setOnClickListener {
            checkPhotoOfVideo(this::pickPhotoFromGalleryWithPermissionCheck, this::pickVideoFromGalleryWithPermissionCheck)
        }

        binding.btnCamera.setOnClickListener {
            checkPhotoOfVideo(this::shootPhotoWithPermissionCheck, this::shootVideoWithPermissionCheck)
        }
    }

    override fun showPhoto(file: File) {
        backToTargetFragment(file)
    }

    override fun showVideo(file: File?) {
        backToTargetFragment(file)
    }

    private fun backToTargetFragment(file: File?) {
        val intent = Intent()
        intent.putExtra(FILE, file as Serializable)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        this.dismiss()
    }

    companion object {
        const val FILE = "file"
    }
}