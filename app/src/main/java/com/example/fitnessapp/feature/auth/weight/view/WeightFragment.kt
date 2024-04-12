package com.example.fitnessapp.feature.auth.weight.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentWeightBinding
import com.example.fitnessapp.feature.auth.register.model.UserModel

class WeightFragment : Fragment() {
    private lateinit var binding: FragmentWeightBinding
    private val args: WeightFragmentArgs by navArgs()
    private lateinit var user: UserModel
    val TAG = "WEIGHT FRAGMENT"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightBinding.inflate(layoutInflater, container, false)
        user = args.userInfo
        user = user.copy(weight = 75) // set initial value as a weight
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPicker()
        onNextClick()
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
        binding.weightBtnNext.setOnClickListener {
            Navigation.findNavController(it)
            Navigation.findNavController(it)
                .navigate(R.id.action_weightFragment_to_loginFragment)
        }
    }
}