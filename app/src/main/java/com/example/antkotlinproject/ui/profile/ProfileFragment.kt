package com.example.antkotlinproject.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseUserPhotoFragment
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.base.pickPhotoFromGalleryWithPermissionCheck
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.databinding.FragmentProfileBinding
import com.example.antkotlinproject.ui.auth.AuthorizationActivity
import com.example.antkotlinproject.utils.toAt
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProfileFragment : BaseUserPhotoFragment() {
    private val viewModel by viewModel<ProfileViewModel>()
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfile()
        exitAction()
        setupViewModel()
    }

    private fun editProfile() {
        binding.btnEdit.setOnClickListener {
            showAlertEdit()
        }

        binding.btnPhoto.setOnClickListener {
            pickPhotoFromGalleryWithPermissionCheck()
        }
    }

    private fun showAlertEdit() {
        val alert = AlertDialog.Builder(requireContext(), R.style.DialogStyle)

        val inflater = layoutInflater.inflate(R.layout.alert_edit, null)
        alert.setView(inflater)
        val negativeButton: Button = inflater.findViewById(R.id.btn_negative)
        val positiveButton: Button = inflater.findViewById(R.id.btn_positive)

        val nameEditText: EditText = inflater.findViewById(R.id.et_name)
        val surnameEditText: EditText = inflater.findViewById(R.id.et_surname)
        val phoneEditText: EditText = inflater.findViewById(R.id.et_phone)
        val emailEditText: EditText = inflater.findViewById(R.id.et_email)

        nameEditText.setText(binding.name.text)
        surnameEditText.setText(binding.surname.text)
        phoneEditText.setText(binding.phoneNumber.text)
        emailEditText.setText(binding.email.text)

        val dialog = alert.create()

        negativeButton.setOnClickListener {
            dialog.dismiss()
        }
        positiveButton.setOnClickListener {
            val data = if (phoneEditText.text.toString() != "") {
                User(
                    phone = phoneEditText.text.toString().toInt(),
                    firstName = nameEditText.text.toString(),
                    lastName = surnameEditText.text.toString(),
                    email = emailEditText.text.toString()
                )
            } else {
                User(
                    firstName = nameEditText.text.toString(),
                    lastName = surnameEditText.text.toString(),
                    email = emailEditText.text.toString()
                )
            }
            viewModel.editUserProfile(data)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun exitAction() {
        binding.btnLeftAccount.setOnClickListener {
            viewModel.clearUserData()
            startActivity(Intent(requireContext(), AuthorizationActivity::class.java))
            activity?.finish()
        }
    }

    private fun setupViewModel() {
        viewModel.event.observe(requireActivity(), Observer {
            when (it) {
                is ProfileEvent.UserProfileFetched -> it.let {
                    binding.email.text = it.item?.email
                    binding.name.text = it.item?.firstName
                    binding.surname.text = it.item?.lastName
                    binding.username.text = it.item?.username?.toAt()
                    Glide.with(binding.photo)
                        .load(it.item?.avatar)
                        .into(binding.photo)
                    if (it.item?.phone != null) binding.phoneNumber.text = it.item.phone.toString()
                    else binding.phoneNumber.text = ""
                }
                is ProfileEvent.UserProfileEdited -> it.let {
                    viewModel.fetchUserProfile()
                }
                is ProfileEvent.UserAvatarChanged -> {
                    viewModel.fetchUserProfile()
                }
            }
        })
    }

    override fun showPhoto(file: File) {
        viewModel.changeImage(file)
    }

    override fun showPhoto1(file: Uri?) {}
}