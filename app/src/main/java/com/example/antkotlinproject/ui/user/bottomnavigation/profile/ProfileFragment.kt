package com.example.antkotlinproject.ui.user.bottomnavigation.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.databinding.FragmentProfileBinding
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.antkotlinproject.utils.isEmptyInputData
import com.example.antkotlinproject.utils.toAt
import org.koin.androidx.viewmodel.ext.android.getViewModel


class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding>(ProfileViewModel::class) {

    override fun attachBinding(
        list: MutableList<FragmentProfileBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentProfileBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = ProfileViewModel::class)
        editProfile()
        changeAvatar()
        exitAction()
    }

    private fun editProfile() {
        binding.btnEdit.setOnClickListener {
            showAlertEdit()
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
            viewModel.editUserProfile(phone = phoneEditText.text.toString().toInt(),
                name = nameEditText.text.toString(), surname = surnameEditText.text.toString(),
                email = emailEditText.toString())
            dialog.dismiss()
            viewModel.fetchUserProfile()
        }
        dialog.show()
    }

    private fun changeAvatar() {
        binding.btnPhoto.setOnClickListener {
            selectPicture()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getSelectedPicture(resultCode, requestCode, data, binding.photo)
    }

    private fun exitAction() {
        binding.btnLeftAccount.setOnClickListener {
            viewModel.clearUserData()
            findNavController().navigate(R.id.action_profileFragment2_to_authorizationActivity)
            activity?.finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
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
            }
        })
    }
}