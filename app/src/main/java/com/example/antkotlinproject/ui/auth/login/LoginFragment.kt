package com.example.antkotlinproject.ui.auth.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.base.ProfileEvent
import com.example.antkotlinproject.databinding.FragmentLoginBinding
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.teacher.MainTeacherActivity
import com.example.antkotlinproject.ui.user.main.MainUserActivity
import com.example.antkotlinproject.utils.PrefsHelper
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding>(
    AuthViewModel::class
) {

    private lateinit var preferences: PrefsHelper

    override fun attachBinding(
        list: MutableList<FragmentLoginBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentLoginBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        preferences = PrefsHelper(requireContext())
        viewModel = getViewModel(clazz = AuthViewModel::class)
        setupListener()
        val formatter = MaskedFormatter("your_mask")
        binding.etLogin.addTextChangedListener(MaskedWatcher(formatter, binding.etLogin))
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
        binding.btnPositive.setOnClickListener { login() }
    }

    private fun login() {
        viewModel.login(
            username = binding.etLogin.text.toString(),
            password = binding.password.text.toString()
        )
    }

    override fun subscribeToLiveData() {
        viewModel.event.observe(this, Observer {
            when (it) {
                is ProfileEvent.UserIsStuffFetched -> {
                    if( it.item.isStuff == true) {
                        preferences.saveIsStuff(true)
                        startActivity(Intent(requireContext(), MainTeacherActivity::class.java))
                        activity?.finish()
                    } else {
                        preferences.saveIsStuff(false)
                        startActivity(Intent(requireContext(), MainUserActivity::class.java))
                        activity?.finish()
                    }
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
}