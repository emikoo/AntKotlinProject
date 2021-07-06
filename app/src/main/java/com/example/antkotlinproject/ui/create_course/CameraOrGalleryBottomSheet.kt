package com.example.antkotlinproject.ui.create_course

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.antkotlinproject.base.BaseAddBottomSheetFragment
import com.example.antkotlinproject.base.shootPhotoWithPermissionCheck
import com.example.antkotlinproject.databinding.LayoutCameraOrGalleryBottomBinding
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

        binding.btnGallery.setOnClickListener {
            pickPhotoFromGallery()
        }

        binding.btnCamera.setOnClickListener {
            shootPhotoWithPermissionCheck()
        }
    }

    override fun showPhoto(file: File) {
        val intent = Intent()
        intent.putExtra(FILE, file as Serializable)
        intent.putExtra(TYPE, 2)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        this.dismiss()
    }

    override fun showPhoto1(file: Uri?) {
        val intent = Intent()
        intent.putExtra(FILE, file as Serializable)
        intent.putExtra(TYPE, 3)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        this.dismiss()
    }

    override fun showVideo(file: File?) {
        TODO("Not yet implemented")
    }

    companion object {
        const val FILE = "file"
        const val TYPE = "type"
    }
}