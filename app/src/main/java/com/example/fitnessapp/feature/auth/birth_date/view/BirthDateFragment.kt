package com.example.fitnessapp.feature.auth.birth_date.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.fitnessapp.R
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
        navigate()
    }

    private fun navigate() {
        binding.birthBtnNext.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_birthDateFragment_to_mobilityFragment)
        }
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