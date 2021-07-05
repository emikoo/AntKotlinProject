package com.example.antkotlinproject.ui.auth.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.antkotlinproject.R
import com.example.antkotlinproject.base.BaseFragment
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.databinding.FragmentRegistrationBinding
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
        if (password1 == password2) viewModel.regUser(user)
    }

    private fun setupViewModel() {
        viewModel.actionRegistrationNewScreen.observe(requireActivity(), Observer {
            if (it == true) {
                findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
            }
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    override fun subscribeToLiveData() {}
}