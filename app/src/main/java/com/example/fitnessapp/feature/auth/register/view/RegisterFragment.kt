package com.example.fitnessapp.feature.auth.register.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onNextClick()
    }

    private fun onNextClick() {
        binding.registerBtnNext.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registerFragment_to_genderFragment)
        }
    }
}