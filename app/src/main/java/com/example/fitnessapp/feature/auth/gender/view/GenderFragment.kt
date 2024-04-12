package com.example.fitnessapp.feature.auth.gender.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentGenderBinding
import com.example.fitnessapp.feature.auth.register.model.UserModel
import com.example.fitnessapp.util.toast.ToastHelper

class GenderFragment : Fragment() {
    private lateinit var binding: FragmentGenderBinding
    private val args: GenderFragmentArgs by navArgs()
    private lateinit var user: UserModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenderBinding.inflate(inflater, container, false)
        user = args.userInfo
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.accent)
        binding.genderFemaleIcon.setOnClickListener {
            setGenderIconColors(Color.WHITE, selectedColor)
            user = user.copy(gender = "female")

        }
        binding.genderMaleIcon.setOnClickListener {
            setGenderIconColors(selectedColor, Color.WHITE)
            user = user.copy(gender = "male")

        }
        binding.genderBtnNext.setOnClickListener {
            if (!user.gender.isNullOrEmpty()) {
                val action = GenderFragmentDirections.actionGenderFragmentToBirthDateFragment(user)
                Navigation.findNavController(it)
                    .navigate(action)
            } else {
                ToastHelper.showToast(it.context, "Make sure that you have selected your sex!!")
            }
        }
    }

    private fun setGenderIconColors(maleColor: Int, femaleColor: Int) {
        binding.genderMaleIcon.setBackgroundColor(maleColor)
        binding.genderFemaleIcon.setBackgroundColor(femaleColor)
    }
}
