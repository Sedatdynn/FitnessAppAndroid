package com.example.fitnessapp.feature.auth.gender.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentGenderBinding
import com.example.fitnessapp.feature.auth.register.viewModel.RegisterViewModel

class GenderFragment : Fragment() {
    private lateinit var binding: FragmentGenderBinding
    private lateinit var registerViewModel: RegisterViewModel
    private val args: GenderFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenderBinding.inflate(inflater, container, false)
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        var user = args.userInfo
        user = user.copy(password = "123456")
        Log.i("user", user.email.toString())
        Log.i("user", user.username.toString())
        Log.i("user", user.password.toString())
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
