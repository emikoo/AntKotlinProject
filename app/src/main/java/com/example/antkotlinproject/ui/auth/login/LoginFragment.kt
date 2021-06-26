package com.example.antkotlinproject.ui.auth.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.databinding.FragmentLoginBinding
import com.example.antkotlinproject.ui.auth.AuthViewModel
import com.example.antkotlinproject.ui.user.main.MainUserActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding>(
    AuthViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentLoginBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentLoginBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = AuthViewModel::class)
        setupListener()
        setupViewModel()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
        binding.btnPositive.setOnClickListener { login() }
    }

    private fun login() {
        viewModel.login(
            username = binding.etLogin.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }

    private fun setupViewModel() {
        viewModel.actionLoginNewScreen.observe(requireActivity(), Observer {
            if (it == true) {
                startActivity(Intent(requireContext(), MainUserActivity::class.java))
                activity?.finish()
            }
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    override fun subscribeToLiveData() {}
}