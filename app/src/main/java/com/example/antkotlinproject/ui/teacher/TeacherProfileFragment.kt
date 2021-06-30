package com.example.antkotlinproject.ui.teacher

import android.app.AlertDialog
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
import com.example.antkotlinproject.databinding.FragmentTeacherProfileBinding
import com.example.antkotlinproject.ui.user.bottomnavigation.profile.ProfileViewModel
import com.example.antkotlinproject.utils.toAt
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TeacherProfileFragment : BaseFragment<ProfileViewModel, FragmentTeacherProfileBinding>(
    ProfileViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentTeacherProfileBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentTeacherProfileBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = ProfileViewModel::class)
        editProfile()
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
            var data = if (phoneEditText.text.toString() != "") {
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
            findNavController().navigate(R.id.action_teacherProfileFragment_to_authorizationActivity2)
            activity?.finish()
        }
    }

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
                is ProfileEvent.UserProfileEdited -> it.let {
                    viewModel.fetchUserProfile()
                }
            }
        })
    }
}