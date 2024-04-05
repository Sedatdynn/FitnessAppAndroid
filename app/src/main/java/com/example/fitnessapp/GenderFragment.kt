package com.example.fitnessapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.fitnessapp.databinding.FragmentGenderBinding

class GenderFragment : Fragment() {
    private lateinit var binding: FragmentGenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedColor = ContextCompat.getColor(requireContext(), R.color.accent)

        binding.genderFemaleIcon.setOnClickListener {
            setGenderIconColors(Color.WHITE, selectedColor)
        }

        binding.genderMaleIcon.setOnClickListener {
            setGenderIconColors(selectedColor, Color.WHITE)
        }

        binding.genderBtnNext.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_genderFragment_to_birthDateFragment)
        }
    }

    private fun setGenderIconColors(maleColor: Int, femaleColor: Int) {
        binding.genderMaleIcon.setBackgroundColor(maleColor)
        binding.genderFemaleIcon.setBackgroundColor(femaleColor)
    }
}
