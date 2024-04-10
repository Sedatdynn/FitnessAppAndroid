package com.example.fitnessapp.feature.welcome.launch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentLaunchBinding


class LaunchFragment : Fragment() {
    private lateinit var binding: FragmentLaunchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.launchBtnEmail.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_launchFragment_to_loginFragment)
        }

        binding.launchTvCreateAccount.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_launchFragment_to_registerFragment)
        }
    }
}