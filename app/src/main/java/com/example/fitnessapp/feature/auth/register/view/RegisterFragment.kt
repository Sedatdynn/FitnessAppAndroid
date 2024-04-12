package com.example.fitnessapp.feature.auth.register.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fitnessapp.databinding.FragmentRegisterBinding
import com.example.fitnessapp.feature.auth.register.model.UserModel
import com.example.fitnessapp.feature.auth.register.viewModel.RegisterViewModel
import com.example.fitnessapp.util.toast.ToastHelper

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerEtUsername.addTextChangedListener { username ->
            registerViewModel.setUsername(username.toString())
        }
        binding.registerEtEmail.addTextChangedListener { email ->
            registerViewModel.setEmail(email.toString())
        }
        binding.registerEtPassword.addTextChangedListener { password ->
            registerViewModel.setPassword(password.toString())
        }
        observeErrorMessage()
        onNextClick(view)
    }

    private fun onNextClick(view: View) {

        binding.registerBtnNext.setOnClickListener {
            registerViewModel.navigateToGender()
            if (registerViewModel.navigateGenderLiveData.value == true) {
                val username = binding.registerEtUsername.text.toString()
                val email = binding.registerEtEmail.text.toString()
                val password = binding.registerEtPassword.text.toString()
                val user = UserModel(username, email, password, "", -1, "", -1, -1)
                val action = RegisterFragmentDirections.actionRegisterFragmentToGenderFragment(user)
                Navigation.findNavController(view)
                    .navigate(action)
            }
        }
    }

    private fun observeErrorMessage() {
        registerViewModel.registerErrorMessage.observe(viewLifecycleOwner, Observer {
            ToastHelper.showToast(requireContext(), it)
        })
    }
}