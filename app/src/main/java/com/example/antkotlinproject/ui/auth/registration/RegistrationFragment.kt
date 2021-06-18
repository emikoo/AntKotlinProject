package com.example.antkotlinproject.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.databinding.FragmentRegistrationBinding
import com.example.antkotlinproject.ui.auth.AuthorizationViewModel
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class RegistrationFragment : BaseFragment<AuthViewModel, FragmentRegistrationBinding>(
    AuthViewModel::class
) {
    override fun attachBinding(
        list: MutableList<FragmentRegistrationBinding>,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) {
        list.add(FragmentRegistrationBinding.inflate(layoutInflater, container, attachToRoot))
    }

    override fun setupViews() {
        viewModel = getViewModel(clazz = AuthViewModel::class)
        setupListener()
    }

    private fun setupListener() {
        binding.btnNegative.setOnClickListener { findNavController().popBackStack() }
        binding.btnPositive.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val username = binding.etRegLogin.text.toString()
        val firsName = binding.etRegName.text.toString()
        val lastName = binding.etRegSurname.text.toString()
        val email = binding.etRegEmail.text.toString()
        val password1 = binding.etRegPassword.text.toString()
        val password2 = binding.etRegCheckPassword.text.toString()
        val user = User(username = username, firstName = firsName, lastName = lastName, email = email,
            password1 = password1, password2 = password2)
        viewModel.regUser(user)
    }

    override fun subscribeToLiveData() {}
}