package com.example.antkotlinproject.ui.auth.registration

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.databinding.FragmentRegistrationBinding
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.teacher.MainTeacherActivity
import com.example.antkotlinproject.ui.user.main.MainUserActivity
import com.example.antkotlinproject.utils.PrefsHelper
import com.example.antkotlinproject.utils.showCongratsDialog
import com.example.antkotlinproject.utils.showToast
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegistrationFragment : BaseFragment<AuthViewModel, FragmentRegistrationBinding>(
    AuthViewModel::class
) {
    private lateinit var preferences: PrefsHelper

    override fun attachBinding(
        list: MutableList<FragmentRegistrationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentRegistrationBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        preferences = PrefsHelper(requireContext())
        viewModel = getViewModel(clazz = AuthViewModel::class)
        setupPasswordStrengthIndicator()
        setupListener()
        setupViewModel()
    }

    private fun setupPasswordStrengthIndicator() {
        binding.passwordSV.attachEditText(binding.etRegPassword)
        binding.passwordContainer.isHintEnabled = false
        binding.checkPasswordContainer.isHintEnabled = false
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
        binding.btnPositive.setOnClickListener { register() }
    }

    private fun register() {
        val arguments = RegistrationFragmentArgs.fromBundle(requireArguments())
        val isStaff = arguments.isStuff
        val username = binding.etRegLogin.text.toString()
        val firsName = binding.etRegName.text.toString()
        val lastName = binding.etRegSurname.text.toString()
        val email = binding.etRegEmail.text.toString()
        val password1 = binding.etRegPassword.text.toString()
        val password2 = binding.etRegCheckPassword.text.toString()
        val user = User(username = username, firstName = firsName, lastName = lastName, email = email, isStuff = isStaff, password1 = password1, password2 = password2)
        if (password1 == password2) {
            viewModel.regUser(user)
        } else {
            showToast(requireContext(), "Пароли не совпадают")
        }
    }

    private fun setupViewModel() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is ProfileEvent.UserIsStuffFetched -> {
                    if( it.item.isStuff == true) {
                        preferences.saveIsStuff(true)
                        startActivity(Intent(requireContext(), MainTeacherActivity::class.java))
                    } else {
                        preferences.saveIsStuff(false)
                        startActivity(Intent(requireContext(), MainUserActivity::class.java))
                    }
                    activity?.finish()
                }
            }
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.loading.observe(requireActivity(), Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })
    }

    override fun subscribeToLiveData() {}
}