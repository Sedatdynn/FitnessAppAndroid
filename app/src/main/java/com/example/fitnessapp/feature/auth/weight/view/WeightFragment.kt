package com.example.fitnessapp.feature.auth.weight.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentHeightBinding
import com.example.fitnessapp.databinding.FragmentWeightBinding
import java.util.Calendar

class WeightFragment : Fragment() {
    private lateinit var binding: FragmentWeightBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeightBinding.inflate(layoutInflater, container, false)
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
                val selectedweight = newVal
                Log.d("SELECTED VALUE: ", selectedweight.toString())
            }
        }
    }

    private fun onNextClick() {
        binding.weightBtnNext.setOnClickListener {
            Navigation.findNavController(it)
            //TODO: ADD NEW NAVIGATION PAGE!!!
//                .navigate(R.id.actionw)
        }
    }
}