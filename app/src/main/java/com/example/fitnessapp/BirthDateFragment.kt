package com.example.fitnessapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.fitnessapp.databinding.FragmentBirthDateBinding
import java.util.Calendar

class BirthDateFragment : Fragment() {
    private lateinit var binding: FragmentBirthDateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBirthDateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNumberPicker()
    }

    private fun configureNumberPicker() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        binding.birthNumberPicker.apply {
            minValue = currentYear - 60
            maxValue = currentYear - 18
            value = 1998
            setOnValueChangedListener { _, _, newVal ->
                val selectedYear = newVal
                Log.d("SELECTED VALUE: ", selectedYear.toString())
            }
        }
    }
}