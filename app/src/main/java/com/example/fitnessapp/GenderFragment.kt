package com.example.fitnessapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.fitnessapp.databinding.FragmentGenderBinding


class GenderFragment : Fragment() {
    private lateinit var binding: FragmentGenderBinding
    private var selectedColor: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedColor = context?.let {
            ContextCompat.getColor(
                it,
                R.color.accent
            )
        } ?: Color.TRANSPARENT
        onMaleClick()
        onFemaleClick()
    }

    private fun onFemaleClick() {
        binding.apply {
            genderFemaleIcon.setOnClickListener {
                genderMaleIcon.setBackgroundColor(Color.WHITE)
                it.setBackgroundColor(selectedColor)
            }
        }
    }

    private fun onMaleClick() {
        binding.apply {
            genderMaleIcon.setOnClickListener {
                genderFemaleIcon.setBackgroundColor(Color.WHITE)
                it.setBackgroundColor(selectedColor)

            }
        }
    }


}