package com.example.fitnessapp.feature.auth.forgot_password.view

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
import com.example.fitnessapp.databinding.FragmentForgotPasswordBinding
import com.example.fitnessapp.feature.auth.forgot_password.viewModel.ForgotPasswordViewModel
import com.example.fitnessapp.util.toast.ToastHelper

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var forgotViewModel: ForgotPasswordViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        forgotViewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.forgotEtEmail.addTextChangedListener { email ->
            forgotViewModel.setEmail(email.toString())
        }
        onVerifyClick()
        observeErrorMessage()
        observeIsLoading()
        observeIsCompleted(view)
    }


    // set disable button while checking email verification
    private fun setButtonEnabled(isEnabled: Boolean) {
        binding.forgotBtn.isEnabled = isEnabled
    }

    // observe error message
    private fun observeErrorMessage() {
        forgotViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                ToastHelper.showToast(requireContext(), errorMessage)
            }
        })
    }

    private fun observeIsLoading() {
        forgotViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                setButtonEnabled(false)
                binding.forgotProgress.visibility = View.VISIBLE
            } else {
                binding.forgotProgress.visibility = View.INVISIBLE
                setButtonEnabled(true)
            }
        })
    }

    private fun observeIsCompleted(view: View) {
        forgotViewModel.isCompleted.observe(viewLifecycleOwner, Observer { isCompleted ->
            if (isCompleted) {
                ToastHelper.showToast(
                    requireContext(),
                    "Your password reset is successful, please check your e-mail address."
                )
                Navigation.findNavController(view)
                    .navigate(R.id.action_forgotPasswordFragment_to_launchFragment)

            }
        })
    }

    private fun onVerifyClick() {
        binding.forgotBtn.setOnClickListener {
            forgotViewModel.checkVerifyEmail()
        }
    }
}