package com.example.fitnessapp.feature.auth.login.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.firebase.AuthResult 
import androidx.navigation.Navigation
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentLoginBinding
import com.example.fitnessapp.util.toast.ToastHelper
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    val TAG = "LoginFragment"
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginEtForgot.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        login()

    }

    private fun login() {
        binding.loginBtn.setOnClickListener {
            if (!binding.loginEtEmail.text.isNullOrEmpty() && !binding.loginEtPassword.text.isNullOrEmpty()) {

                lifecycleScope.launch {
                    val result = FirebaseManager.signIn(
                        binding.loginEtEmail.text.toString(),
                        binding.loginEtPassword.text.toString()
                    )
                    when (result) {
                        is AuthResult .Success -> {
                            val user = result.user
                            Log.i(TAG, "email: ${user.email}")
                            Navigation.findNavController(it)
                                .navigate(R.id.action_loginFragment_to_homeFragment)
                        }

                        is AuthResult .Error -> {
                            val errorMessage = result.errorMessage
                            Log.e(TAG, errorMessage)
                            ToastHelper.showToast(it.context, errorMessage)
                        }

                        else -> {}
                    }
                }
            } else {
                ToastHelper.showToast(
                    it.context,
                    "email and password can't be null!"
                )
            }

        }
    }

}

