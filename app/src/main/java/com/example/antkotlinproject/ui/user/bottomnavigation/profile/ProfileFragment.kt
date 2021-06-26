package com.example.antkotlinproject.ui.user.bottomnavigation.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.databinding.FragmentProfileBinding
import com.example.antkotlinproject.utils.PrefsHelper
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
        setupViewModel()
        changeAvatar()
        exitAction()
    }

    private fun setupViewModel() {
        viewModel = getViewModel(clazz = ProfileViewModel::class)
        viewModel.fetchUserProfile(2)
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
                    binding.name.text = "${it.item?.firstName} ${it.item?.lastName}"
                    binding.username.text = it.item?.username?.toAt()
                    Glide.with(binding.photo)
                        .load(it.item?.avatar)
                        .into(binding.photo)
                    binding.phoneNumber.text = it.item?.phone.toString()
                }
            }
        })
    }
}