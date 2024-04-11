package com.example.fitnessapp.feature.auth.login.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentLoginBinding
import com.example.fitnessapp.feature.auth.login.viewModel.LoginViewModel
import com.example.fitnessapp.util.toast.ToastHelper


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginEtForgot.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.loginEtEmail.addTextChangedListener { email ->
            loginViewModel.setEmail(email.toString())
        }
        binding.loginEtPassword.addTextChangedListener { password ->
            loginViewModel.setPassword(password.toString())
        }

        observeLoginError(view.context)
        observeNavigation(view)
        login()
    }

    private fun observeLoginError(context: Context) {
        loginViewModel.loginErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                ToastHelper.showToast(context, errorMessage)
            }
        })
    }

    private fun login() {
        binding.loginBtn.setOnClickListener {
            loginViewModel.signIn()
        }
    }


    private fun observeNavigation(view: View) {
        loginViewModel.navigateToHomeLiveData.observe(
            viewLifecycleOwner,
            Observer { navigateToHome ->
                navigateToHome?.let {
                    if (navigateToHome) {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            })
    }

}

