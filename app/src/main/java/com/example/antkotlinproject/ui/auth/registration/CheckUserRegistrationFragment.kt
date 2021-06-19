package com.example.antkotlinproject.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.databinding.FragmentCheckUserRegistrationBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.utils.showToast

class CheckUserRegistrationFragment :
    BaseFragment<AuthorizationViewModel, FragmentCheckUserRegistrationBinding>(
        AuthorizationViewModel::class) {

    private var isPupilClicked: Boolean = false
    private var isTeacherClicked: Boolean = false
    private var isStuff: Boolean =  false

    override fun attachBinding(
        list: MutableList<FragmentCheckUserRegistrationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean) {
        list.add(FragmentCheckUserRegistrationBinding.inflate( layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnPupil.setOnClickListener {
            isPupilClicked = !isPupilClicked
            isTeacherClicked = false
            isChecked()
        }
        binding.btnTeacher.setOnClickListener {
            isTeacherClicked = !isTeacherClicked
            isPupilClicked = false
            isChecked()
        }
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
        binding.btnPositive.setOnClickListener {
            if (isTeacherClicked || isPupilClicked) {
                isStuff = isTeacherClicked
                argAction(isStuff)
            }
            else { showToast(requireContext(), "Выберите один вариант") }
        }
    }

    private fun isChecked() {
        changeCheckedState(isTeacherClicked, binding.btnTeacher)
        changeCheckedState(isPupilClicked, binding.btnPupil)
    }

    private fun changeCheckedState(flag: Boolean, button: Button) {
        if (flag){
            button.setBackgroundColor(resources.getColor(R.color.color_green))
            button.setTextColor(resources.getColor(R.color.color_bej))
        } else {
            button.setBackgroundColor(resources.getColor(R.color.color_bej))
            button.setTextColor(resources.getColor(R.color.color_green))
        }
    }

    private fun argAction(flag: Boolean) {
        val directions
                = CheckUserRegistrationFragmentDirections.actionCheckUserRegistrationFragmentToRegistrationFragment2(
            flag
        )
        findNavController().navigate(directions)
    }

    override fun subscribeToLiveData() {}
}