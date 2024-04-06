package com.example.fitnessapp.feature.auth.height.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentBirthDateBinding
import com.example.fitnessapp.databinding.FragmentHeightBinding
import java.util.Calendar


class HeightFragment : Fragment() {
    private lateinit var binding: FragmentHeightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeightBinding.inflate(layoutInflater, container, false)
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
                val selectedHeight = newVal
                Log.d("SELECTED VALUE: ", selectedHeight.toString())
            }
        }
    }

    private fun onNextClick() {
        binding.heightBtnNext.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_heightFragment_to_weightFragment)
        }
    }
}