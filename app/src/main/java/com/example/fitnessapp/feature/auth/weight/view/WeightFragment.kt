package com.example.fitnessapp.feature.auth.weight.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentWeightBinding
import com.example.fitnessapp.feature.auth.weight.viewModel.WeightViewModel
import com.example.fitnessapp.util.toast.ToastHelper
import com.example.model.UserModel


class WeightFragment : Fragment() {
    private lateinit var binding: FragmentWeightBinding
    private lateinit var weightViewModel: WeightViewModel

    private val args: WeightFragmentArgs by navArgs()
    private lateinit var user: UserModel
    val TAG = "WEIGHT FRAGMENT"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightBinding.inflate(layoutInflater, container, false)
        weightViewModel = ViewModelProvider(this)[WeightViewModel::class.java]
        user = args.userInfo
        user = user.copy(weight = 75) // set initial value as a weight
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPicker()
        onNextClick()
        observeErrorMessage()
        observeIsLoading()
        observeIsCompleted(view)
    }

    private fun configureNumberPicker() {
        binding.weightNumberPicker.apply {
            minValue = 40
            maxValue = 220
            value = 75
            setOnValueChangedListener { _, _, newVal ->
                user = user.copy(weight = newVal)
                Log.d("SELECTED VALUE: ", newVal.toString())
            }
        }
    }

    private fun onNextClick() {
        Log.d(TAG, user.toString())
        if (weightViewModel.isLoading.value == false) {
            binding.weightBtnNext.setOnClickListener {
                //TODO: create viewModel for weight fragment, if no error navigate to login!
                weightViewModel.calculateTotalPoints(user)
            }
        }
    }

    // observe error message
    private fun observeErrorMessage() {
        weightViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                ToastHelper.showToast(requireContext(), errorMessage)
            }
        })
    }

    private fun observeIsCompleted(view: View) {
        weightViewModel.isCompleted.observe(viewLifecycleOwner, Observer { isCompleted ->
            if (isCompleted) {
                ToastHelper.showToast(
                    requireContext(),
                    "Your registration has been completed successfully."
                )
                Navigation.findNavController(view)
                    .navigate(R.id.action_weightFragment_to_loginFragment)

            }
        })
    }

    private fun observeIsLoading() {
        weightViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                setButtonEnabled(false)
                binding.weightProgress.visibility = View.VISIBLE
            } else {
                binding.weightProgress.visibility = View.INVISIBLE
                setButtonEnabled(true)
            }
        })
    }


    // set disable button while registration
    private fun setButtonEnabled(isEnabled: Boolean) {
        binding.weightBtnNext.isEnabled = isEnabled
    }
}