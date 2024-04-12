package com.example.fitnessapp.feature.auth.height.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.databinding.FragmentHeightBinding
import com.example.model.UserModel


class HeightFragment : Fragment() {
    private lateinit var binding: FragmentHeightBinding
    private val args: HeightFragmentArgs by navArgs()
    private lateinit var user: UserModel
    val TAG = "HEIGHT FRAGMENT"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeightBinding.inflate(layoutInflater, container, false)
        user = args.userInfo
        user = user.copy(height = 170) //set initial value as a height
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPicker()
        onNextClick()
    }

    private fun configureNumberPicker() {
        binding.heightNumberPicker.apply {
            minValue = 140
            maxValue = 220
            value = 170
            setOnValueChangedListener { _, _, newVal ->
                user = user.copy(height = newVal)
                Log.d("SELECTED VALUE: ", newVal.toString())
            }
        }
    }

    private fun onNextClick() {
        Log.i(TAG, user.toString())
        binding.heightBtnNext.setOnClickListener {
            val action =
                HeightFragmentDirections.actionHeightFragmentToWeightFragment(user)
            Navigation.findNavController(it)
                .navigate(action)
        }
    }
}