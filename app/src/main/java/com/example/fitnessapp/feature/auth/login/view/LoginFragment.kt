package com.example.fitnessapp.feature.auth.login.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.firebase.FirebaseManager
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentLoginBinding
import com.example.fitnessapp.util.toast.ToastHelper


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
                FirebaseManager.signIn(
                    binding.loginEtEmail.text.toString(), binding.loginEtPassword.text.toString(),
                    onError = { message ->
                        Log.e(TAG, message)
                        ToastHelper.showToast(it.context, message)
                    },
                    onSuccess = { uid ->
                        Log.i(TAG, "uid: $uid")
                        Navigation.findNavController(it)
                            .navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                )
            } else {
                ToastHelper.showToast(
                    it.context,
                    "email and password can't be null!"
                )
            }

        }
    }

}